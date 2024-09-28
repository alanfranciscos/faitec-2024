package com.eventify.eventify.port.dao.event;

import com.eventify.eventify.models.event.EventHeader;

import java.util.List;

public interface ListPaginatedFromUser {

    List<EventHeader> listPaginatedHeaderFromUser(final int limit, final int offset, final int accountId);
}
