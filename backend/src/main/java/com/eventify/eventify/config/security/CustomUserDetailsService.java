package com.eventify.eventify.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.repository.account.AccountRepository;

import java.util.ArrayList;

import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.repository.account.password.AccountPasswordHistoryRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository repository;

    @Autowired
    private AccountPasswordHistoryRepository accountPasswordHistoryRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = this.repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        AccountPasswordHistory accountPassword = accountPasswordHistoryRepository
                .findByAccountIdAndActive(account.getId(), true)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return new org.springframework.security.core.userdetails
                .User(account.getEmail(), accountPassword.getPassword(), new ArrayList<>());
    }
}