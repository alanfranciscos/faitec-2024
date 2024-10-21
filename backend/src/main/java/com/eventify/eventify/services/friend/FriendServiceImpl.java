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

import java.time.ZonedDateTime;
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
            if(friend.getFriendId() != accountId){
                FriendHeader friendHeader = new FriendHeader(
                        friend.getFriendId(),
                        friendAccount.getUsername(),
                        friendAccount.getImageData(),
                        friend.getAcceptedAt()
                );
                friendHeaders.add(friendHeader);
            } else {
                Friend friend1 = friendDao.readByAccountIdAndFriendId(accountId, friend.getAccountId());
                Account friendAccount1 = accountService.getAccountById(friend1.getAccountId());
                FriendHeader friendHeader = new FriendHeader(
                        friend1.getAccountId(),
                        friendAccount1.getUsername(),
                        friendAccount1.getImageData(),
                        friend1.getAcceptedAt()
                );
                friendHeaders.add(friendHeader);
            }
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
    public int createFriend(String email) {
        if(email == null){
            throw new RuntimeException("Email is requiered");
        }
        Account account = accountService.getAccountRequest();
        Account friendAccount = accountService.getAccountByEmail(email);
        if(friendAccount == null){
            throw new RuntimeException("Invalid email");
        }

        boolean isFriend = friendDao.isFriend(account.getId(), friendAccount.getId());
        if(isFriend){
            throw new RuntimeException("Already friends");
        }

        ZonedDateTime sendedAt = ZonedDateTime.now();

        Friend friend = new Friend();
        friend.setAccountId(account.getId());
        friend.setFriendId(friendAccount.getId());
        friend.setSendedAt(sendedAt);
        friend.setAcceptedAt(null);

        int id = friendDao.save(friend);

        return id;
    }
    @Override
    public void deleteFriend(int friendId) {

        if (friendId < 0) {
            throw new RuntimeException("ID less than 0");
        }
        friendDao.deleteById(friendId);
//        Account account = accountService.getAccountRequest();
//        int accountId = account.getId();
//
//        List<Friend> friends = friendDao.listFriendByAccountId(accountId, 10, 0);
//
//        List<FriendHeader> friendHeaders = new ArrayList<>();
//
//        for (Friend friend : friends) {
//            if (friend.getAcceptedAt() == null || friend.getId() != friendId) {
//                continue;
//            }
//            friendDao.deleteById(friendId);
//            return true;
//        }
//        return false;
    }

    @Override
    public void updateAceptedAt(int friendId) {
        Account account = accountService.getAccountRequest();
        int accountId = account.getId();
        friendDao.updateAceptedAt(friendId);
    }

    @Override
    public void rejectFriend(int friendId) {
        if (friendId < 0) {
            throw new RuntimeException("ID less than 0");
        }
        friendDao.deleteById(friendId);
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
            throw new RuntimeException("ID less than 0");
        }

        Account account = accountService.getAccountRequest();
        Account friendAccount = accountService.getAccountById(id);
        if(friendAccount == null){
            throw new RuntimeException("Invalid ID");
        }

        boolean isFriend = friendDao.isFriend(account.getId(), friendAccount.getId());
        if(isFriend){
            friendDao.deleteById(id);
        } else {
            throw new RuntimeException("Is not friend");
        }

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
