package com.eventify.eventify.controller.account.authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.eventify.dto.auth.LoginRequestDTO;
import com.eventify.eventify.dto.auth.ResponseDTO;
import com.eventify.eventify.services.account.authentication.AuthenticationServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/account/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        String token = authenticationService.Login(body.email(), body.password());

        return ResponseEntity.ok(new ResponseDTO(token));
    }

}
