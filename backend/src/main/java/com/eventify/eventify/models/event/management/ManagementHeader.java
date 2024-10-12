package com.eventify.eventify.models.event.management;

import com.eventify.eventify.models.event.participate.RoleParticipateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManagementHeader {
    private int id;
    private int participate_id;
    private ZonedDateTime managment_at;
    private String type_action;
}
