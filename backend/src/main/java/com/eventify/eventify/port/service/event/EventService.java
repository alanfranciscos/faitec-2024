package com.eventify.eventify.port.service.event;

import com.eventify.eventify.models.event.EventHeader;

import java.util.List;

public interface EventService {

    List<EventHeader> listPaginatedFromUser(final int limit, final int offset);
}
