package com.eventify.eventify.port.dao.expense;

import com.eventify.eventify.dto.event.EventExpansesResponse;
import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.port.dao.crud.CrudDao;

import java.util.List;

public interface ExpenseDao extends CrudDao<Expense> {
    EventExpansesResponse getExpensesByAccountId(int idAccount);
}
