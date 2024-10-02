package com.eventify.eventify.services.friend;

import com.eventify.eventify.dto.friend.FriendListResponse;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.friend.Friend;
import com.eventify.eventify.models.friend.FriendHeader;
import com.eventify.eventify.port.dao.friend.FriendDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.friend.FriendService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    private final FriendDao friendDao;
    private final AccountService accountService;

    public FriendServiceImpl(FriendDao friendDao, AccountService accountService) {
        this.friendDao = friendDao;
        this.accountService = accountService;
    }


    @Override
    public FriendListResponse listFriendByAccountId(final int limit, final int offset) {
        Account account = accountService.getAccountRequest();
        int accountId = account.getId();
        List<Friend> friends = friendDao.listFriendByAccountId(accountId, limit, offset);

        List<FriendHeader> friendHeaders = new ArrayList<>();

        for (Friend friend : friends) {
            if (friend.getAcceptedAt() == null) {
                continue;
            }
            Account friendAccount = accountService.getAccountById(friend.getFriendId());

            FriendHeader friendHeader = new FriendHeader(
                    friend.getId(),
                    friendAccount.getUsername(),
                    friendAccount.getImageData(),
                    friend.getAcceptedAt()
            );

            friendHeaders.add(friendHeader);
        }


        FriendListResponse response = new FriendListResponse(
                friendHeaders,
                friendDao.QuantityOfFriendsByAccountIdAndAcepted(accountId)
        );

        return response;
    }
}
