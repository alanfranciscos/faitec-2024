package com.eventify.eventify.dto.event;

import java.time.ZonedDateTime;

public record EventDatesResponse(ZonedDateTime startDate, ZonedDateTime endDate) {
}
