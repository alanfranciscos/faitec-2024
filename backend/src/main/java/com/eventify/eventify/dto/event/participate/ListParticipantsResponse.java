package com.eventify.eventify.dto.event.participate;

import java.time.ZonedDateTime;
import java.util.List;

public record ListParticipantsResponse(List<Participant> participants) {
    public record Participant(
            int id,
            String name,
            ZonedDateTime aceptedAt,
            String roleParticipate
    ) {
    }
}
