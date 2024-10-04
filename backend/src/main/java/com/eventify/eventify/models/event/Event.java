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

    public Event(String title, String information, ZonedDateTime createdAt, String localName, String cepAddress, String stateAddress, String cityAddress, String neighborhoodAddress, String numberAddress, String streetAddress, String complementAddress, double latitude, double longitude, ZonedDateTime dateStart, ZonedDateTime dateEnd, EventStageEnum stage, String pixKey) {
        this.title = title;
        this.information = information;
        this.createdAt = createdAt;

        this.localName = localName;
        this.cepAddress = cepAddress;
        this.stateAddress = stateAddress;
        this.cityAddress = cityAddress;
        this.neighborhoodAddress = neighborhoodAddress;
        this.numberAddress = numberAddress;
        this.streetAddress = streetAddress;
        this.complementAddress = complementAddress;
        this.latitude = latitude;
        this.longitude = longitude;

        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.stage = stage;
        this.pixKey = pixKey;
    }
}
