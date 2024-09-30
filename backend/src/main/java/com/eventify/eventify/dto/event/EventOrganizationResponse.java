package com.eventify.eventify.dto.event;

import java.time.ZonedDateTime;

public record EventOrganizationResponse(
        int id,
        ZonedDateTime createdOn,
        String createdBy,
        int numberOfParticipants,
        String status
) {
}
