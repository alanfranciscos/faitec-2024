package com.eventify.eventify.services.expense;


import com.eventify.eventify.dto.event.EventExpansesResponse;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.port.dao.expense.ExpenseDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.crud.CrudService;
import com.eventify.eventify.port.service.event.expense.ExpenseService;
import com.eventify.eventify.port.service.event.participate.ParticipateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseDao expenseDao;
    private final AccountService accountService;
    private final ParticipateService participateService;

    public ExpenseServiceImpl(ExpenseDao expenseDao, AccountService accountService, ParticipateService participateService) {
        this.expenseDao = expenseDao;
        this.accountService = accountService;
        this.participateService = participateService;
    }

    @Override
    public int create(Expense entity) {
        if(entity == null){
            return 0;
        }
        int id = expenseDao.save(entity);
        return id;
    }

    @Override
    public void delete(int id) {
        if (id < 0) {
            return;
        }
        expenseDao.deleteById(id);
    }

    @Override
    public Expense findById(int id) {
        if(id < 0){
            return null;
        }
        Expense expense = expenseDao.readById(id);
        return expense;
    }

    @Override
    public List<Expense> findAll() {
        List<Expense> expenses = expenseDao.readAll();
        return expenses;
    }

    @Override
    public void update(int id, Expense entity) {
        Expense expense = findById(id);
        if (expense == null) {
            return;
        }
        expenseDao.updateInformation(id, entity);
    }

    @Override
    public EventExpansesResponse getExpensesByAccountId() {
        Account account = accountService.getAccountRequest();
        if (account == null){
            throw new RuntimeException("Null account");
        }

        EventExpansesResponse eventExpansesResponse = expenseDao.getExpensesByAccountId(account.getId());

        return eventExpansesResponse;
    }
}
