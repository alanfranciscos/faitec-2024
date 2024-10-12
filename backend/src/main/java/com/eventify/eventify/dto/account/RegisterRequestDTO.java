package com.eventify.eventify.dto.account;

public record RegisterRequestDTO(
        String username,
        String email,
        String password
) {
}