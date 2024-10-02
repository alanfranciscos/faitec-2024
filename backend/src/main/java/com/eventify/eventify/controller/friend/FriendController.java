package com.eventify.eventify.controller.friend;

import com.eventify.eventify.dto.friend.FriendListResponse;
import com.eventify.eventify.port.service.friend.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<FriendListResponse> listPaginatedFromUser(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        FriendListResponse response = friendService
                .listFriendByAccountId(
                        limit,
                        offset
                );

        return ResponseEntity.ok(response);
    }

}
