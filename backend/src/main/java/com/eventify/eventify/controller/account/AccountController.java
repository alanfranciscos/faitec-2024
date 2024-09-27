package com.eventify.eventify.controller.account;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eventify.eventify.dto.account.ForgotPasswordRequestDTO;
import com.eventify.eventify.dto.account.ForgotPasswordResponseDTO;
import com.eventify.eventify.dto.account.RegisterRequestDTO;
import com.eventify.eventify.dto.account.VerifyAccountRequestDTO;
import com.eventify.eventify.dto.account.VerifyAccountResponseDTO;
import com.eventify.eventify.services.account.AccountServiceImpl;

import lombok.RequiredArgsConstructor;

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

}
