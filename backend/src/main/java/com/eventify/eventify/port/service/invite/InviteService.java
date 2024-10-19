package com.eventify.eventify.port.service.invite;

import com.eventify.eventify.dto.invite.InviteListResponse;
import com.eventify.eventify.models.account.Account;

public interface InviteService {
    InviteListResponse listInviteEventByAccountId(final int limit, final int offset);

    InviteListResponse listInviteFriendByAccountId(final int limit, final int offset);

    void updateAceptedAt(final int eventId);

    void rejectEvent(final int eventId);
}
