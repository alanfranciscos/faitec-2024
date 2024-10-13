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

    @GetMapping("/entities")
    public ResponseEntity<List<Friend>> getEntities(){
        List<Friend> friends = friendService.findAll();
        return ResponseEntity.ok().body(friends);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Friend> getEntityById(@PathVariable final int id){
        Friend friend = friendService.findById(id);
        return ResponseEntity.ok().body(friend);
    }

    @PostMapping("/create")
    public ResponseEntity<Friend> createEntity(@RequestBody final FriendCreateRequest data){
        int id = friendService.createFriend(data.email());
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEntity(@PathVariable final int id, @RequestBody final Friend data){
        friendService.update(id, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Friend> deleteEntity(@PathVariable final int id){
        friendService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
