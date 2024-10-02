package com.eventify.eventify.models.event.participate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Participate {
    private int id;
    private int accountId;
    private int eventId;
    private RoleParticipateEnum roleParticipate;
    private boolean active;
    private ZonedDateTime sendedAt;
    private ZonedDateTime acceptedAt;

}
