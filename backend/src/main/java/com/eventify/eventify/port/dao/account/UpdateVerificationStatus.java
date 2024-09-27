package com.eventify.eventify.port.dao.account;

public interface UpdateVerificationStatus {

    void updateVerificationStatus(
            final int id,
            final boolean isVerified
    );
}
