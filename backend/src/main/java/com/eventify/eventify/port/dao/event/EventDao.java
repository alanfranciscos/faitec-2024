package com.eventify.eventify.port.dao.event;

import com.eventify.eventify.models.event.EventOrganization;

public interface EventDao extends ListPaginatedFromUser, TotalFromUser, Expanses {

    boolean hasAccessToEvent(final int eventId, final int accountId);

    EventOrganization getOrganizationById(final int id);

}
