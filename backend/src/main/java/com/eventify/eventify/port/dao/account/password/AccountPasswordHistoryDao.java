package com.eventify.eventify.port.dao.account.password;

import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.port.dao.crud.CreateDao;
import com.eventify.eventify.port.dao.crud.DeleteDao;

public interface AccountPasswordHistoryDao extends
        CreateDao<AccountPasswordHistory>,
        DeleteDao,
        FindByAccountIdDao,
        UpdateActiveAndStagingStatus {

}
