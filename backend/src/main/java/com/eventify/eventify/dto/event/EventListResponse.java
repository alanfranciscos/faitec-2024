package com.eventify.eventify.dto.event;

import com.eventify.eventify.models.event.Event;

import java.util.List;

public record EventListResponse(List<Event> events) {

}
