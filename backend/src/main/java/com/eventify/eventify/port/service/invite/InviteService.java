package com.eventify.eventify.port.service.invite;

import com.eventify.eventify.dto.invite.InviteListResponse;

public interface InviteService {
    InviteListResponse listInviteEventByAccountId(final int limit, final int offset);

    InviteListResponse listInviteFriendByAccountId(final int limit, final int offset);
}
