package com.eventify.eventify.repository.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventify.eventify.models.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
}
