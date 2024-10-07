package com.eventify.eventify.dto.event.management;

import java.time.ZonedDateTime;

public record ManagementCreateResponse(

        Integer id,
        Integer participate_id,
        ZonedDateTime managment_at,
        String type_action
) {
}
