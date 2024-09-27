package com.eventify.eventify.port.dao.event;

import com.eventify.eventify.models.event.Event;

import java.util.List;

public interface ListPaginatedFromUser {

    List<Event> listPaginatedFromUser(final int limit, final int offset, final int accountId);
}
