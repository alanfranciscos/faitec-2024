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
public class Event {

    private Integer id;
    private String title;
    private String information;
    private ZonedDateTime createdAt;

    private String localName;
    private String cepAddress;
    private String stateAddress;
    private String cityAddress;
    private String neighborhoodAddress;
    private String numberAddress;
    private String streetAddress;
    private String complementAddress;
    private double latitude;
    private double longitude;

    private ZonedDateTime dateStart;
    private ZonedDateTime dateEnd;
    private EventStageEnum stage;
    private String pixKey;
}
