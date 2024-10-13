package com.eventify.eventify.port.dao.event;

public interface Expanses {

    double totalExpenses(final int eventId);
    int getTotalExpensesForPagination(final int eventId);
}
