package com.eventify.eventify.port.service.event;

import com.eventify.eventify.models.event.EventExpanses;

import java.util.List;

public interface ExpenseForEventService {

    double totalExpanses(final int eventId);

    List<EventExpanses> getExpansesById(final int id, final int limit, final int offset);

    int getTotalExpensesForPagination(final int eventId);

}
