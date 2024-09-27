package com.eventify.eventify.port.service.email;

public interface EmailService {
    void sendConfirmationCode(String email, String code);
}
