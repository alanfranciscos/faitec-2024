package com.eventify.eventify.services.account.authentication;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.port.dao.account.AccountDao;
import com.eventify.eventify.port.dao.account.password.AccountPasswordHistoryDao;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final AccountDao accountDao;
    private final AccountPasswordHistoryDao accountPasswordHistoryDao;

    public AuthenticationService(AccountDao accountDao, AccountPasswordHistoryDao accountPasswordHistoryDao) {
        this.accountDao = accountDao;
        this.accountPasswordHistoryDao = accountPasswordHistoryDao;
    }

    /**
     * Verifies the user's password for the given email.
     * 
     * @param email    the email of the user
     * @param password the password to verify
     * @return the Account object if the password is correct
     * @throws RuntimeException if the email or password is empty, or if the email
     *                          or password is incorrect
     */
    public Account verifyUserPassword(String email, String password) {
        if (email == null || password == null) {
            throw new RuntimeException("User or password empty");
        }

        Account account = accountDao.readByEmail(email);
        if (account == null) {
            throw new RuntimeException("User or password incorrect");
        }

        AccountPasswordHistory accountPasswordHistory =  accountPasswordHistoryDao
                .findByAccountIdAndActive(account.getId(), true)
                .orElseThrow(() -> new RuntimeException("User or password incorrect"));

        if (!accountPasswordHistory.verifyMathPassword(password)) {
            throw new RuntimeException("User or password incorrect");
        }

        return account;
    }
}
