package com.eventify.eventify.dto.event;

import java.time.ZonedDateTime;
import java.util.List;

public record EventExpansesResponse(List<Expanse> expanses) {
    public record Expanse(
            ZonedDateTime date,
            String description,
            double amount
    ) {
    }
}
