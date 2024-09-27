package com.eventify.eventify.port.dao.account.password;

import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import java.util.List;
import java.util.Optional;

public interface FindByAccountIdDao {
    List<AccountPasswordHistory> findByAccountId(Integer accountId);
    Optional<AccountPasswordHistory> findByAccountIdAndActive(Integer accountId, boolean active);
    Optional<AccountPasswordHistory> findByAccountIdAndStaging(Integer accountId, boolean staging);
}
