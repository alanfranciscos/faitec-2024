package com.eventify.eventify.port.service.event;

import com.eventify.eventify.models.event.Event;

import java.util.List;

public interface EventService {

    List<Event> listPaginatedFromUser(final int limit, final int offset);
}
