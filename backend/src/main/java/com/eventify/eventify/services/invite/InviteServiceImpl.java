package com.eventify.eventify.services.invite;

import com.eventify.eventify.dto.invite.InviteListResponse;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.friend.Friend;
import com.eventify.eventify.models.invite.Invite;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.event.EventService;
import com.eventify.eventify.port.service.event.participate.ParticipateService;
import com.eventify.eventify.port.service.friend.FriendService;
import com.eventify.eventify.port.service.invite.InviteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InviteServiceImpl implements InviteService {
    private final ParticipateService participateService;
    private final EventService eventService;

    private final FriendService friendService;
    private final AccountService accountService;

    public InviteServiceImpl(
            ParticipateService participateService,
            EventService eventService,
            FriendService friendService,
            AccountService accountService
    ) {
        this.participateService = participateService;
        this.eventService = eventService;
        this.friendService = friendService;
        this.accountService = accountService;
    }

//    EventListResponse listPaginatedFromUserAndNotAcepted(final int limit, final int offset);

    @Override
    public InviteListResponse listInviteEventByAccountId(int limit, int offset) {
        List<Participate> participateList = participateService.
                listPaginatedFromUserAndNotAceptedAndNotIsOwner(limit, offset);


        List<Invite> eventInviteList = new ArrayList<>();
        int totalEventInvites = 0;

        for (Participate participate : participateList) {
            Event event = eventService.getEventById(participate.getEventId());
            totalEventInvites += 1;
            Invite invite = new Invite(
                    participate.getId(),
                    event.getTitle(),
                    participate.getSendedAt()
            );

            eventInviteList.add(invite);
        }

        return new InviteListResponse(
                eventInviteList, totalEventInvites
        );
    }

    @Override
    public InviteListResponse listInviteFriendByAccountId(int limit, int offset) {
        List<Friend> friendList = friendService.
                listPaginatedFromUserAndNotAcepted(limit, offset);


        List<Invite> friendInviteList = new ArrayList<>();
        int totalEventInvites = 0;

        for (Friend friend : friendList) {
            Account sendedAccount = accountService.getAccountById(friend.getFriendId());
            totalEventInvites += 1;

            Invite invite = new Invite(
                    friend.getId(),
                    sendedAccount.getUsername(),
                    friend.getSendedAt()
            );

            friendInviteList.add(invite);
        }

        return new InviteListResponse(
                friendInviteList, totalEventInvites
        );

    }
}
