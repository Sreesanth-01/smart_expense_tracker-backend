package com.expensetracker.smart_expense_tracker.service;

import java.util.List;

import com.expensetracker.smart_expense_tracker.dto.ExpenseRequest;
import com.expensetracker.smart_expense_tracker.model.Expense;

public interface ExpenseService {
    void addExpense(ExpenseRequest request,String email);

    List<Expense> getExpenses(String email);
    
}
