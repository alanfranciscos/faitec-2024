package com.eventify.eventify.port.service.event;

import com.eventify.eventify.dto.event.EventListResponse;

public interface EventService {

    EventListResponse listPaginatedFromUser(final int limit, final int offset);
}
