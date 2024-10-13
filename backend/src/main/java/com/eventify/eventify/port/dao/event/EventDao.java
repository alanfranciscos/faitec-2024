package com.eventify.eventify.port.dao.event;

import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventDate;
import com.eventify.eventify.models.event.EventExpanses;
import com.eventify.eventify.models.event.EventOrganization;
import com.eventify.eventify.port.dao.crud.CrudDao;
import com.eventify.eventify.port.dao.crud.ReadDao;

import java.util.List;

public interface EventDao extends ListPaginatedFromUser, TotalFromUser, Expanses, CrudDao<Event> {

    boolean hasAccessToEvent(final int eventId, final int accountId);

    EventOrganization getOrganizationById(final int id);

    EventDate getDateById(final int id);

    List<EventExpanses> getExpansesById(final int id);

    int getTotalParticipantsForPagination(final int eventId);
}
