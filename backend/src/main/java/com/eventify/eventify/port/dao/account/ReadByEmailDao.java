package com.eventify.eventify.port.dao.account;

import com.eventify.eventify.models.account.Account;

public interface ReadByEmailDao {
    Account readByEmail(final String email);
}
