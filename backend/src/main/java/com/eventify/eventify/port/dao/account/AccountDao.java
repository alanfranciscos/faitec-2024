package com.eventify.eventify.port.dao.account;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.port.dao.crud.CreateDao;
import com.eventify.eventify.port.dao.crud.DeleteDao;
import com.eventify.eventify.port.dao.crud.ReadDao;

public interface AccountDao extends
        ReadDao<Account>,
        ReadByEmailDao,
        CreateDao<Account>,
        UpdateVerificationStatus,
        DeleteDao {

}