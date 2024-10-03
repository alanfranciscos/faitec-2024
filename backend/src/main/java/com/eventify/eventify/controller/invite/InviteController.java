package com.eventify.eventify.controller.invite;

import com.eventify.eventify.dto.invite.InviteListResponse;
import com.eventify.eventify.port.service.invite.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/invite")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

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

}
