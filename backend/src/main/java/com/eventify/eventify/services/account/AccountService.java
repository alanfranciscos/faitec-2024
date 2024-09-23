package com.eventify.eventify.services.account;

import com.eventify.eventify.dto.account.RegisterRequestDTO;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.port.dao.account.AccountDao;
import com.eventify.eventify.port.dao.account.password.AccountPasswordHistoryDao;
import com.eventify.eventify.services.email.EmailService;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountDao accountDao;
    
    private final AccountPasswordHistoryDao accountPasswordHistoryDao;
    private final EmailService emailService;

    public AccountService(
            AccountDao accountDao,
            AccountPasswordHistoryDao accountPasswordHistoryDao,
            EmailService emailService) {
        this.accountDao = accountDao;
        this.accountPasswordHistoryDao = accountPasswordHistoryDao;
        this.emailService = emailService;
    }

    @Value("${validation.code.expiration.minutes}")
    private int codeExpirationMinutes;

    private String generateVerificationCodString() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += String.valueOf((int) (Math.random() * 10));
        }
        return code;
    }

    public Account RegisterUser(RegisterRequestDTO registerRequestDTO) {
        Account account = this.accountDao
                .readByEmail(registerRequestDTO.email());

        if (account == null) {
            throw new RuntimeException("User already exists");
        }

        Account newAccount = new Account();

        newAccount.setUsername(registerRequestDTO.username());
        newAccount.setEmail(registerRequestDTO.email());
        newAccount.setImageData(registerRequestDTO.imageData());

        try {
            this.accountDao.save(newAccount);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("message: Failed to create user", e);
        }

        AccountPasswordHistory passwordHistory = new AccountPasswordHistory(newAccount);
        passwordHistory.setPassword(registerRequestDTO.password());
        passwordHistory.setActive(false);
        passwordHistory.setStaging(true);

        String codeGenerated = generateVerificationCodString();
        passwordHistory.setVerificationCode(codeGenerated, codeExpirationMinutes);

        try {
            accountPasswordHistoryDao.save(passwordHistory);
        } catch (IllegalArgumentException e) {
            accountDao.deleteById(newAccount.getId());
            throw new RuntimeException("message: Failed to create user", e);
        }

        emailService.sendConfirmationCode(newAccount.getEmail(), passwordHistory.getVerificationCode());

        return newAccount;
    }

    public String forgotPassword(String email, String password) {
        Account account = accountDao.readByEmail(email);
        if (account == null) {
            throw new RuntimeException("User not found");
        }

        Optional<AccountPasswordHistory> passwordInStaging = accountPasswordHistoryDao
                .findByAccountIdAndStaging(account.getId(), true);

        if (passwordInStaging.isPresent()) {
            accountPasswordHistoryDao.deleteById(passwordInStaging.get().getId());
        }

        List<AccountPasswordHistory> passwordsUsed = accountPasswordHistoryDao
                .findByAccountId(account.getId());

        for (AccountPasswordHistory passwordUsed : passwordsUsed) {
            if (passwordUsed.getPassword().equals(password)) {
                throw new RuntimeException("Password already used");
            }
        }

        AccountPasswordHistory passwordHistory = new AccountPasswordHistory(account);
        passwordHistory.setPassword(password);
        passwordHistory.setActive(false);
        passwordHistory.setStaging(true);

        String codeGenerated = generateVerificationCodString();
        passwordHistory.setVerificationCode(codeGenerated, codeExpirationMinutes);

        try {
            accountPasswordHistoryDao.save(passwordHistory);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save verification code", e);
        }

        emailService.sendConfirmationCode(account.getEmail(), passwordHistory.getVerificationCode());

        return account.getEmail();
    }

    public boolean verifyAccount(String email, String code) {
        Account account = accountDao.readByEmail(email);
        if (account == null) {
            throw new RuntimeException("User or password not found");
        }

        AccountPasswordHistory userPassword = accountPasswordHistoryDao
                .findByAccountIdAndStaging(account.getId(), true)
                .orElseThrow(() -> new RuntimeException("User or password not found"));

        if (userPassword.getCodeValidUntil().isBefore(ZonedDateTime.now())) {
            throw new RuntimeException("Code expired");
        }

        if (!userPassword.getVerificationCode().equals(code)) {
            throw new RuntimeException("Invalid code");
        }

        userPassword.setActive(true);
        userPassword.setStaging(false);

        try {
            accountPasswordHistoryDao.save(userPassword);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save password", e);
        }

        return true;
    }
}
