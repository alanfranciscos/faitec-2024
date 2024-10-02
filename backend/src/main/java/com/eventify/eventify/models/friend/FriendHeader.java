package com.eventify.eventify.models.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendHeader {
    private int id;
    private String username;
    private byte[] image;
    private ZonedDateTime dateStartFriendship;
}
