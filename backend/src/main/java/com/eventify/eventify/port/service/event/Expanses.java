package com.eventify.eventify.port.service.event;

import com.eventify.eventify.models.event.EventExpanses;

import java.util.List;

public interface Expanses {

    double totalExpanses(final int eventId);

    List<EventExpanses> getExpansesById(final int id);
}
