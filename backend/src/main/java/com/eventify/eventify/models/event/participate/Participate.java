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

    public Participate(int eventId, RoleParticipateEnum roleParticipate, boolean active){
        this.accountId = accountId;
        this.eventId = eventId;
        this.roleParticipate = roleParticipate;
        this.active = active;
        this.sendedAt = sendedAt;
        this.acceptedAt = acceptedAt;
    }

}
