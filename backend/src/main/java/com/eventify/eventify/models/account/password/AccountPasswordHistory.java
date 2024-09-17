package com.eventify.eventify.models.account.password;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.eventify.eventify.models.account.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "account_id")
    private Long accountId;

    @Getter
    @Column(name = "user_password")
    private String password;

    @Getter
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Getter
    @Setter
    private boolean active;

    @Getter
    @Setter
    private boolean staging;

    @Getter
    @Column(name = "verification_code")
    private String verificationCode;

    @Getter
    @Column(name = "code_valid_until")
    private ZonedDateTime codeValidUntil;

    public AccountPasswordHistory(Account account) {
        this.createdAt = ZonedDateTime.now();
        this.accountId = account.getId();
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
