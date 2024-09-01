package com.eventify.eventify.services.account.authentication;

import org.springframework.stereotype.Service;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.repository.account.AccountRepository;
import com.eventify.eventify.repository.account.password.AccountPasswordHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountRepository accountRepository;
    private final AccountPasswordHistoryRepository accountPasswordHistoryRepository;

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

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User or password incorrect"));

        AccountPasswordHistory accountPasswordHistory =  accountPasswordHistoryRepository
                .findByAccountIdAndActive(account.getId(), true)
                .orElseThrow(() -> new RuntimeException("User or password incorrect"));

        if (!accountPasswordHistory.verifyMathPassword(password)) {
            throw new RuntimeException("User or password incorrect");
        }

        return account;
    }
}
