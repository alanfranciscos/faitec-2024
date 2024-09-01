package com.eventify.eventify.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${validation.code.expiration.minutes}")
    private String codeExpirationMinutes;

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendConfirmationCode(String email, String code) {
        String subject = "Eventify - Código de Verificação";
        String text = BodyEmail.creadeHtmlBodyToSendCode(code, codeExpirationMinutes);
        this.sendEmail(email, subject, text);
    }

}
