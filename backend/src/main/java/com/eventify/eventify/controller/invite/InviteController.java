package com.eventify.eventify.controller.invite;

import com.eventify.eventify.dto.invite.InviteListResponse;
import com.eventify.eventify.models.friend.Friend;
import com.eventify.eventify.port.service.friend.FriendService;
import com.eventify.eventify.port.service.invite.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/invite")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;
    private final FriendService friendService;

    @GetMapping("/list/event")
    public ResponseEntity<InviteListResponse> listInvites(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {

        InviteListResponse response = inviteService.listInviteEventByAccountId(10, 0);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/friend")
    public ResponseEntity<InviteListResponse> listInvitesFriend(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {

        InviteListResponse response = inviteService.listInviteFriendByAccountId(10, 0);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/list/friend/{id}/accept")
    public ResponseEntity<Void> acceptInvitation(@PathVariable final int id){
        friendService.updateAceptedAt(id);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * @param id - friendId, não AccountId
     * @return
     */
    @PutMapping("/list/friend/{id}/reject")
    public ResponseEntity<Void> rejectInvitation(@PathVariable final int id){
        friendService.rejectFriend(id);
        return ResponseEntity.ok().build();
    }

}
