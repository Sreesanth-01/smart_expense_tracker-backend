package com.expensetracker.smart_expense_tracker.service;

import java.time.LocalDate;
import java.util.List;

import com.expensetracker.smart_expense_tracker.dto.ExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.UpdateExpenseRequest;
import com.expensetracker.smart_expense_tracker.model.Expense;

public interface ExpenseService {
    void addExpense(ExpenseRequest request,String email);

    List<Expense> getExpenses(String email);
    
    Expense updateExpense(long id,String email,UpdateExpenseRequest request);

    void deleteExpense(long id,String email);

    List<Expense> getExpensesByDateRange(String email,LocalDate startDate,LocalDate endDate);

    List<Expense> getExpensesByCategory(String email,String category);
}
