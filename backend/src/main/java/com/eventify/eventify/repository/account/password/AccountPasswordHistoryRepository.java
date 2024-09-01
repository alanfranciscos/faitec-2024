package com.eventify.eventify.repository.account.password;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.eventify.eventify.models.account.password.AccountPasswordHistory;

public interface AccountPasswordHistoryRepository extends JpaRepository<AccountPasswordHistory, Long> {
    List<AccountPasswordHistory> findByAccountId(Long accountId);
    Optional<AccountPasswordHistory> findByAccountIdAndActive(Long accountId, boolean active);
    Optional<AccountPasswordHistory> findByAccountIdAndStaging(Long accountId, boolean staging);

}