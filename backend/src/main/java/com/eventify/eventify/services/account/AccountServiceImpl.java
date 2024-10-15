package com.eventify.eventify.services.account;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.port.dao.account.AccountDao;
import com.eventify.eventify.port.dao.account.password.AccountPasswordHistoryDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.gcp.GcpStorageService;
import com.eventify.eventify.services.email.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${validation.code.expiration.minutes}")
    private int codeExpirationMinutes;

    private final AccountDao accountDao;

    private final AccountPasswordHistoryDao accountPasswordHistoryDao;
    private final EmailServiceImpl emailService;
    private final GcpStorageService gcpStorageService;

    public AccountServiceImpl(
            AccountDao accountDao,
            AccountPasswordHistoryDao accountPasswordHistoryDao,
            EmailServiceImpl emailService, GcpStorageService gcpStorageService) {
        this.accountDao = accountDao;
        this.accountPasswordHistoryDao = accountPasswordHistoryDao;
        this.emailService = emailService;
        this.gcpStorageService = gcpStorageService;
    }

    public Integer RegisterUser(String username, String email, String password) {
        boolean accountExist = userExist(email);
        if (accountExist) {
            throw new RuntimeException("User already exists");
        }

        int accountId = createAccount(username, email);

        String codeGenerated = generateVerificationCodString();
        try {
            createAccountPasswordHistory(accountId, password, codeGenerated);
        } catch (Exception e) {
            accountDao.deleteById(accountId);
            throw new RuntimeException("Failed to create user", e);
        }

        boolean skip = true;
        if (!skip) {
            emailService.sendConfirmationCode(email, codeGenerated);
        }

        return accountId;
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

        String codeGenerated = generateVerificationCodString();
        try {
            createAccountPasswordHistory(account.getId(), password, codeGenerated);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user", e);
        }

        emailService.sendConfirmationCode(account.getEmail(), codeGenerated);

        return account.getEmail();
    }

    @Override
    public boolean verifyAccount(int id, String code) {
        Account account = accountDao.readById(id);
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

        try {
            if (!account.isVerified()) {
                accountDao.updateVerificationStatus(account.getId(), !account.isVerified());
            }

            boolean passwordIsActive = true;
            boolean passwordIsStaging = false;
            accountPasswordHistoryDao.updateActiveAndStagingStatus(
                    account.getId(),
                    passwordIsActive,
                    passwordIsStaging
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to save password", e);
        }

        return true;
    }

    @Override
    public Account getAccountRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();

        if (account == null) {
            throw new RuntimeException("User not authenticated");
        }

        return account;
    }

    @Override
    public Account getAccountById(int accountId) {
        try {
            return accountDao.readById(accountId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read account", e);
        }
    }

    @Override
    public void updateImage(int id, MultipartFile imageData) {
        String imageUrl = "";

        try {
            String fileName = "1.png";
            String bucketPath = "users/" + id + "/";
            imageUrl = gcpStorageService.uploadImage(imageData, fileName, bucketPath);
        } catch (IOException e) {
            throw new RuntimeException("Bucket error: ", e);
        }

        try {
            accountDao.updateImage(id, imageUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update image in database: ", e);
        }
    }

    @Override
    public void deleteAccount(int id) {
        try {
            accountDao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete account", e);
        }
    }

    // UTILS
    private String generateVerificationCodString() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += String.valueOf((int) (Math.random() * 10));
        }
        return code;
    }

    private boolean userExist(String email) {
        Account account = this.accountDao
                .readByEmail(email);

        if (account == null) {
            return false;
        }

        return true;
    }

    private int createAccount(String username, String email) {
        Account account = new Account();

        account.setUsername(username);
        account.setEmail(email);
        account.setImageData(null);

        try {
            int accountId = this.accountDao.save(account);
            return accountId;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("message: Failed to create user", e);
        }
    }

    private int createAccountPasswordHistory(int accountId, String password, String code) {
        AccountPasswordHistory passwordHistory = new AccountPasswordHistory(accountId);
        passwordHistory.setPassword(password);
        passwordHistory.setActive(false);
        passwordHistory.setStaging(true);
        passwordHistory.setVerificationCode(code, codeExpirationMinutes);

        try {
            int id = accountPasswordHistoryDao.save(passwordHistory);
            return id;
        } catch (IllegalArgumentException e) {

            throw new RuntimeException("message: Failed to create user", e);
        }
    }
}
