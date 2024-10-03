package com.eventify.eventify.models.invite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Invite {
    private int id;
    private String title;
    private ZonedDateTime sendedAt;
}
