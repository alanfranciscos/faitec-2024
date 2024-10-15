package com.eventify.eventify.controller.friend;

import com.eventify.eventify.dto.friend.FriendCreateRequest;
import com.eventify.eventify.dto.friend.FriendListResponse;
import com.eventify.eventify.models.friend.Friend;
import com.eventify.eventify.port.service.friend.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping()
    public ResponseEntity<FriendListResponse> listPaginatedFromUser(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        FriendListResponse response = friendService.listFriendByAccountId(limit,offset);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-friends")
    public ResponseEntity<List<Friend>> getFriends(){
        List<Friend> friends = friendService.findAll();
        return ResponseEntity.ok().body(friends);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Friend> getFriendById(@PathVariable final int id){
        Friend friend = friendService.findById(id);
        return ResponseEntity.ok().body(friend);
    }

    /**
     *
     * @param data
     *     {
     *         "email": "user1@example.com",
     *     }
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Friend> createFriend(@RequestBody final FriendCreateRequest data){
        int id = friendService.createFriend(data.email());
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Void> updateFriend(@PathVariable final int id, @RequestBody final Friend data){
//        friendService.update(id, data);
//        return ResponseEntity.ok().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Friend> deleteFriend(@PathVariable final int id){
        final boolean response = friendService.deleteFriend(id);
        return response ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

}
