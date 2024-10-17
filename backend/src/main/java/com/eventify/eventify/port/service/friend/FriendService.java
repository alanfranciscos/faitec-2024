package com.eventify.eventify.port.service.friend;

import com.eventify.eventify.dto.friend.FriendListResponse;
import com.eventify.eventify.models.friend.Friend;
import com.eventify.eventify.port.service.crud.CrudService;

import java.util.List;


public interface FriendService extends CrudService<Friend> {
    FriendListResponse listFriendByAccountId(final int limit, final int offset);

    List<Friend> listPaginatedFromUserAndNotAcepted(int limit, int offset);

    int createFriend(String email);

    boolean deleteFriend(int friendId);

    void updateAceptedAt(final int friendId);

    void rejectFriend(final int friendId);
}
