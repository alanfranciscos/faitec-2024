package com.eventify.eventify.dto.friend;

import com.eventify.eventify.models.friend.FriendHeader;

import java.util.List;

public record FriendListResponse(
        List<FriendHeader> friends,
        int total
) {
}
