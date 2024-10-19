package com.eventify.eventify.dto.event;

import java.time.ZonedDateTime;
import java.util.List;

public record EventExpansesResponse(List<Expanse> expanses, int total) {
    public record Expanse(
            int id,
            ZonedDateTime date,
            String description,
            double amount
    ) {
    }
}
