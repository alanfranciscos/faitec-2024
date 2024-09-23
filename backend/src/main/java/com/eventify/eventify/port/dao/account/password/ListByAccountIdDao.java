package com.eventify.eventify.port.dao.account.password;

import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import java.util.List;

public interface ListByAccountIdDao {
    List<AccountPasswordHistory> listByAccountId(Integer accountId);
}
