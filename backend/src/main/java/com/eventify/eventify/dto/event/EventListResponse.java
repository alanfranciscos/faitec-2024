package com.eventify.eventify.dto.event;

import com.eventify.eventify.models.event.EventHeader;

import java.util.List;

public record EventListResponse(List<EventHeader> events, int total) {
}
