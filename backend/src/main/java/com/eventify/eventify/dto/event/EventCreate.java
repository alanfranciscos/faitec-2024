package com.eventify.eventify.dto.event;

import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventStageEnum;

import java.time.ZonedDateTime;

public record EventCreate(
         String title,
         String information,
         ZonedDateTime createdAt,

         String localName,
         String cepAddress,
         String stateAddress,
         String cityAddress,
         String neighborhoodAddress,
         String numberAddress,
         String streetAddress,
         String complementAddress,
         double latitude,
         double longitude,

         ZonedDateTime dateStart,
         ZonedDateTime dateEnd,
         EventStageEnum stage,
         String pixKey
) {
}
