package com.eventify.eventify.port.dao.account;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.port.dao.crud.CreateDao;
import com.eventify.eventify.port.dao.crud.DeleteDao;

public interface AccountDao extends 
        ReadByEmailDao,
        CreateDao<Account>,
        DeleteDao {
}
