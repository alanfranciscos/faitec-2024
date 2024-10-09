package com.eventify.eventify.services.friend;

import com.eventify.eventify.dto.friend.FriendListResponse;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.models.friend.Friend;
import com.eventify.eventify.models.friend.FriendHeader;
import com.eventify.eventify.port.dao.friend.FriendDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.crud.CrudService;
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

    @Override
    public List<Friend> listPaginatedFromUserAndNotAcepted(int limit, int offset) {
        Account account = accountService.getAccountRequest();
        int accountId = account.getId();
        List<Friend> friends = friendDao.listPaginatedFromUserAndNotAcepted(accountId, limit, offset);

        return friends;
    }

    @Override
    public int create(Friend entity) {
        if(entity == null){
            return 0;
        }
        int id = friendDao.save(entity);
        return id;
    }

    @Override
    public void delete(int id) {
        if (id < 0) {
            return;
        }
        friendDao.deleteById(id);
    }

    @Override
    public Friend findById(int id) {
        if(id < 0){
            return null;
        }
        Friend friend = friendDao.readById(id);
        return friend;
    }

    @Override
    public List<Friend> findAll() {
        List<Friend> friends = friendDao.readAll();
        return friends;
    }

    @Override
    public void update(int id, Friend entity) {
        Friend friend = findById(id);
        if (friend == null) {
            return;
        }
        friendDao.updateInformation(id, entity);
    }
}
