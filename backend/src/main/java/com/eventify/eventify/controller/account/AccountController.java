package com.eventify.eventify.controller.account;

import com.eventify.eventify.dto.account.ForgotPasswordRequestDTO;
import com.eventify.eventify.dto.account.ForgotPasswordResponseDTO;
import com.eventify.eventify.dto.account.VerifyAccountRequestDTO;
import com.eventify.eventify.dto.account.VerifyAccountResponseDTO;
import com.eventify.eventify.models.account.Account;
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
    public ResponseEntity<String> register(@RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           @RequestParam(value = "image", required = false) MultipartFile imageData) {
        int accountId = this.accountService.RegisterUser(
                username,
                email,
                password
        );
        if (imageData != null) {
            try {
                this.accountService.updateImage(accountId, imageData);
            } catch (Exception e) {
                this.accountService.deleteAccount(accountId);
                throw new RuntimeException("Failed to create user", e);
            }
        }

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
        this.accountService.verifyAccount(body.id(), body.code());
        return ResponseEntity.ok(new VerifyAccountResponseDTO("Account verified"));
    }

    @PatchMapping("/{id}/image-profile")
    public ResponseEntity<String> updateImageProfile(@PathVariable int id, @RequestParam("image") MultipartFile imageData) {
        this.accountService.updateImage(id, imageData);
        return ResponseEntity.ok("Image updated successfully");
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id) {
        Account account = this.accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @RequestMapping()
    public ResponseEntity<Account> getAuthAccount() {
        Account account = accountService.getAccountRequest();
        return ResponseEntity.ok(account);
    }

    @PutMapping
    public ResponseEntity<Void> updatAccount(@RequestParam(value = "image", required = false) MultipartFile imageData,
                                             @RequestParam(value = "username", required = false) String username,
                                             @RequestParam(value = "password", required = false) String password,
                                             @RequestParam(value = "confirmedPassword", required = false) String confirmedPassword){

        Account account = accountService.getAccountRequest();
        int accountId = account.getId();

        if(!username.isBlank()) {
            Account updatedAccount = new Account();
            updatedAccount.setUsername(username);
            accountService.updateAccount(accountId, updatedAccount);
        }

        accountService.updateImage(accountId, imageData);


        if(!password.isBlank() && !confirmedPassword.isBlank()){
            accountService.updatePassword(accountId, password, confirmedPassword);
        }

        return ResponseEntity.ok().build();
    }

}
