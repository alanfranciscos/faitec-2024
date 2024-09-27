package com.eventify.eventify.port.dao.account.password;

public interface UpdateActiveAndStagingStatus {

    void updateActiveAndStagingStatus(
            final int accountId,
            final boolean active,
            final boolean staging
    );
}
