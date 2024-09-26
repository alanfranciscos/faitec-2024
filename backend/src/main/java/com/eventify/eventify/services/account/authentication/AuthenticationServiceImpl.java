package com.eventify.eventify.services.account.authentication;

import com.eventify.eventify.config.security.TokenService;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.port.dao.account.AccountDao;
import com.eventify.eventify.port.dao.account.password.AccountPasswordHistoryDao;
import com.eventify.eventify.port.service.account.authentication.AuthenticationService;

import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl  implements AuthenticationService{

    private final AccountDao accountDao;
    private final AccountPasswordHistoryDao accountPasswordHistoryDao;
    private final TokenService tokenService;

    public AuthenticationServiceImpl(
        AccountDao accountDao,
        AccountPasswordHistoryDao accountPasswordHistoryDao, 
        TokenService tokenService
    ) {
        this.accountDao = accountDao;
        this.accountPasswordHistoryDao = accountPasswordHistoryDao;
        this.tokenService = tokenService;
    }

    public String Login(String email, String password) {

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

        String token = this.tokenService.generateToken(account.getEmail());
        return token;
    }
}
