package com.eventify.eventify.dto.event.management;

import com.eventify.eventify.models.event.management.Management;

import java.time.ZonedDateTime;
import java.util.List;

public record ListManagementsResponse(List<Management> managements) {
    public record Management(
            int id,
            int participate_id,
            ZonedDateTime managment_at,
            String type_action
    ) {
    }
}
