package com.eventify.eventify.port.dao.friend;

import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.friend.Friend;
import com.eventify.eventify.port.dao.crud.CrudDao;
import com.eventify.eventify.port.dao.crud.ReadDao;

import java.util.List;

public interface FriendDao extends ReadDao<Friend>, CrudDao<Friend> {
    List<Friend> listFriendByAccountId(final int accountId, final int limit, final int offset);

    List<Friend> listPaginatedFromUserAndNotAcepted(final int accountId, final int limit, final int offset);

    int QuantityOfFriendsByAccountIdAndAcepted(final int accountId);
}
