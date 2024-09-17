package com.eventify.eventify.controller.account.authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.eventify.config.security.TokenService;
import com.eventify.eventify.dto.auth.LoginRequestDTO;
import com.eventify.eventify.dto.auth.ResponseDTO;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.services.account.authentication.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/account/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        Account account = authenticationService
                .verifyUserPassword(body.email(), body.password());

        if (account == null) {
            return ResponseEntity.badRequest().build();
        }

        String token = this.tokenService.generateToken(account);
        return ResponseEntity.ok(new ResponseDTO(account.getEmail(), token));
    }

}
