package com.eventify.eventify.controller.account;

import com.eventify.eventify.dto.account.*;
import com.eventify.eventify.services.account.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountService;

    @PostMapping()
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO body) {
        int accountId = this.accountService.RegisterUser(
                body.username(),
                body.email(),
                body.password(),
                body.imageData()
        );

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accountId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/forget-password")
    public ResponseEntity<ForgotPasswordResponseDTO> forgetPassword(@RequestBody ForgotPasswordRequestDTO body) {
        String email = this.accountService.forgotPassword(body.email(), body.password());
        return ResponseEntity.ok(new ForgotPasswordResponseDTO(email));
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyAccountResponseDTO> verify(@RequestBody VerifyAccountRequestDTO body) {
        this.accountService.verifyAccount(body.email(), body.code());
        return ResponseEntity.ok(new VerifyAccountResponseDTO("Account verified"));
    }

    @PatchMapping("/{id}/image-profile")
    public ResponseEntity<String> updateImageProfile(@PathVariable int id, @RequestParam("image") MultipartFile imageData) {
        this.accountService.updateImage(id, imageData);
        return ResponseEntity.ok("Image updated successfully");
    }

}
