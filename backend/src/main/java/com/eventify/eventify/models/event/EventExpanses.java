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
public class EventExpanses {
    private ZonedDateTime createdAt;
    private String about;
    private double cost;
}
