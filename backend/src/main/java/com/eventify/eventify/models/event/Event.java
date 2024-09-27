package com.eventify.eventify.models.event;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event {

    private Integer id;
    private String title;
    private String information;
    private ZonedDateTime createdAt;

    private String cepAddress;
    private String stateAddress;
    private String cityAddress;
    private String neighborhoodAddress;
    private String numberAddress;
    private String streetAddress;

    private ZonedDateTime dateStart;
    private ZonedDateTime dateEnd;
    private EventStageEnum stage;
    private String pixKey;
}
