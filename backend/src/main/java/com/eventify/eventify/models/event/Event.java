package com.eventify.eventify.models.event;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("eventname")
    private String title;

    @JsonProperty("eventdescription")
    private String information;

    private ZonedDateTime createdAt;

    @JsonProperty("local_name")
    private String localName;

    @JsonProperty("cep_address")
    private String cepAddress;

    @JsonProperty("state_address")
    private String stateAddress;

    @JsonProperty("city_address")
    private String cityAddress;

    @JsonProperty("neighborhood_address")
    private String neighborhoodAddress;

    @JsonProperty("number_address")
    private String numberAddress;

    @JsonProperty("street_address")
    private String streetAddress;

    @JsonProperty("complement_address")
    private String complementAddress;

    private double latitude;
    private double longitude;

    @JsonProperty("date_start")
    private ZonedDateTime dateStart;

    @JsonProperty("date_end")
    private ZonedDateTime dateEnd;

    private EventStageEnum stage;

    @JsonProperty("pix_key")
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

    public Event(String eventName, String eventDescription, ZonedDateTime date_start, ZonedDateTime date_end) {
        this.title = eventName;
        this.information = eventDescription;
        this.dateStart = date_start;
        this.dateEnd = date_end;
    }
}
