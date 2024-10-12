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
public class EventHeader {
    private Integer id;
    private String title;
    private String information;

    private ZonedDateTime dateStart;
    private ZonedDateTime dateEnd;
    private EventStageEnum stage;

    private String profileImage;
}
