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
public class ParticipateHeader {
    private int id;
    private String name;
    private ZonedDateTime sendedAt;
    private RoleParticipateEnum roleParticipate;
}
