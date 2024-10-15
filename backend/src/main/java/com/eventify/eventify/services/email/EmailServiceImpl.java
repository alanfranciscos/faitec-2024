package com.eventify.eventify.services.email;

import com.eventify.eventify.port.service.email.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = Logger
            .getLogger(EmailServiceImpl.class.getName());

    @Value("${sendgrid.api.key}")
    private String sendgridApiKey;

    @Value("${validation.code.expiration.minutes}")
    private String codeExpirationMinutes;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private void sendEmail(String to, String subject, String text) throws IOException {
        Email from = new Email(this.fromEmail);
        Email toEmail = new Email(to);
        Content content = new Content("text/html", text);
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(this.sendgridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if (response.getStatusCode() != 202) {
                throw new IOException("Error sending email: " + response.getBody());
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }

    }

    @Override
    public void sendConfirmationCode(String toEmail, String code, int userId) {
        String activationUrl = "http://localhost:4200//account/" + userId + "/confirmation";

        String subject = "Eventify - Código de Verificação";
        String text = BodyEmail.creadeHtmlBodyToSendCode(
                code,
                codeExpirationMinutes,
                activationUrl
        );
        try {
            this.sendEmail(toEmail, subject, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
