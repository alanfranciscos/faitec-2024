package com.eventify.eventify.port.service.friend;

import com.eventify.eventify.dto.friend.FriendListResponse;


public interface FriendService {
    FriendListResponse listFriendByAccountId(final int limit, final int offset);
}
