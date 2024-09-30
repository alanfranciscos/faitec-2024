package com.eventify.eventify.models.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventOrganization {
    private int id;
    private ZonedDateTime createdOn;
    private String createdBy;
    private int numberOfParticipants;
    private EventStageEnum status;
}
