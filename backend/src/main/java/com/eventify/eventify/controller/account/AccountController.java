package com.eventify.eventify.controller.Account;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.eventify.dto.account.ForgotPasswordRequestDTO;
import com.eventify.eventify.dto.account.ForgotPasswordResponseDTO;
import com.eventify.eventify.dto.account.RegisterRequestDTO;
import com.eventify.eventify.dto.account.VerifyAccountRequestDTO;
import com.eventify.eventify.dto.account.VerifyAccountResponseDTO;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.services.account.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO body) {
        Account account = this.accountService.RegisterUser(body);
        if (account == null) {
            return ResponseEntity.badRequest().body("User not created");
        }
        URI uri = URI.create("/account/verify/" + account.getId());
        return ResponseEntity.created(uri)
                .body("User created with success!");
    }

    @PostMapping("/forget-password")
    public ResponseEntity<ForgotPasswordResponseDTO> forgetPassword(@RequestBody ForgotPasswordRequestDTO body) {
        String email = this.accountService.forgotPassword(body.email(), body.password());

        if (email == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(new ForgotPasswordResponseDTO(email));
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyAccountResponseDTO> verify(@RequestBody VerifyAccountRequestDTO body) {
        if (!this.accountService.verifyAccount(body.email(), body.code())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new VerifyAccountResponseDTO("Account verified"));
    }

}
