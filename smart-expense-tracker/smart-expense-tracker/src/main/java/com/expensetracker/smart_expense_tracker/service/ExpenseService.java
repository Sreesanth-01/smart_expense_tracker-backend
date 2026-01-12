package com.expensetracker.smart_expense_tracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.expensetracker.smart_expense_tracker.dto.CategorySummaryResponse;
import com.expensetracker.smart_expense_tracker.dto.ExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.MonthlySummaryResponse;
import com.expensetracker.smart_expense_tracker.dto.UpdateExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.YearlySummaryResponse;
import com.expensetracker.smart_expense_tracker.model.Expense;

public interface ExpenseService {
    void addExpense(ExpenseRequest request,String email);

    Page<Expense> getExpenses(String email,Pageable pageable);
    
    Expense updateExpense(long id,String email,UpdateExpenseRequest request);

    void deleteExpense(long id,String email);

    List<Expense> getExpensesByDateRange(String email,LocalDate startDate,LocalDate endDate);

    List<Expense> getExpensesByCategory(String email,String category);

    MonthlySummaryResponse getMonthlySummary(String email, int year, int month);

    YearlySummaryResponse getYearlySummary(String email, int year);

    List<CategorySummaryResponse> getCategorySummary(String email);


}
