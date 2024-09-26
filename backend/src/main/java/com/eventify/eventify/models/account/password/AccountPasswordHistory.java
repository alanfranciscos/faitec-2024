package com.eventify.eventify.models.account.password;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.*;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_password")
public class AccountPasswordHistory {
    @Id
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private Integer accountId;

    @Getter
    private String password;

    @Getter
    @Setter
    private ZonedDateTime createdAt;

    @Getter
    @Setter
    private boolean active;

    @Getter
    @Setter
    private boolean staging;

    @Getter
    @Setter
    private String verificationCode;

    @Getter
    @Setter
    private ZonedDateTime codeValidUntil;

    public AccountPasswordHistory(int accountId) {
        this.createdAt = ZonedDateTime.now();
        this.accountId = accountId;
    }

    public boolean verifyMathPassword(String inputPassword) {
        BCrypt.Result result = BCrypt.verifyer()
                .verify(inputPassword.toCharArray(), password);
        return result.verified;
    }

    public void setPassword(String password) {
        String passwordHashed = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        this.password = passwordHashed;
    }

    public void setPasswordFromDao(String password) {
        this.password = password;
    }


    public void setVerificationCode(String verificationCode, int codeExpirationMinutes) {
        this.verificationCode = verificationCode;
        this.codeValidUntil = ZonedDateTime.now().plusMinutes(codeExpirationMinutes);
    }

    public boolean equalsPassword(String inputPassword) {
        BCrypt.Result result = BCrypt.verifyer()
                .verify(inputPassword.toCharArray(), password);
        return result.verified;
    }

}
