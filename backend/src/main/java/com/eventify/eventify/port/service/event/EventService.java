package com.eventify.eventify.port.service.event;

import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventDate;
import com.eventify.eventify.models.event.EventOrganization;

public interface EventService extends Expanses {

    EventListResponse listPaginatedFromUser(final int limit, final int offset);

    EventOrganization getOrganizationById(final int id);

    EventDate getDateById(final int id);

    Event getEventById(final int id);
}
