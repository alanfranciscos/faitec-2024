package com.eventify.eventify.services.expense;


import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.port.dao.expense.ExpenseDao;
import com.eventify.eventify.port.service.crud.CrudService;

import java.util.List;

public class ExpenseServiceImpl implements CrudService<Expense> {

    private final ExpenseDao expenseDao;

    public ExpenseServiceImpl(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    @Override
    public int create(Expense entity) {
        if(entity == null){
            return 0;
        }
//        if(entity.getFullName().isEmpty() || entity.getPassword().isEmpty() || entity.getEmail().isEmpty()){
//            return 0;
//        }
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
        Expense expense = expenseDao.readById(id);
        if (expense == null) {
            return;
        }
        expenseDao.updateInformation(id, entity);
    }
}
