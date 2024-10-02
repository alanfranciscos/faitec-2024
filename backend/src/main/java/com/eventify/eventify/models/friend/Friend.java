package com.eventify.eventify.models.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Friend {
    private int id;
    private int accountId;
    private int friendId;
    private ZonedDateTime sendedAt;
    private ZonedDateTime acceptedAt;
}
