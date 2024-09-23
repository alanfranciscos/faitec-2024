package com.eventify.eventify.config.security;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.port.dao.account.AccountDao;
import com.eventify.eventify.port.dao.account.password.AccountPasswordHistoryDao;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountPasswordHistoryDao accountPasswordHistoryDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = this.accountDao.readByEmail(email);
        if (account == null) {
                throw new UsernameNotFoundException("User not found");
        }

        AccountPasswordHistory accountPassword = accountPasswordHistoryDao
                .findByAccountIdAndActive(account.getId(), true)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return new org.springframework.security.core.userdetails
                .User(account.getEmail(), accountPassword.getPassword(), new ArrayList<>());
    }
}