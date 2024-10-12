package com.eventify.eventify.port.service.event.expense;

import com.eventify.eventify.dto.event.EventExpansesResponse;
import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.port.service.crud.CrudService;

import java.util.List;

public interface ExpenseService extends CrudService<Expense> {
    List<EventExpansesResponse> getExpensesByAccountId();
}
