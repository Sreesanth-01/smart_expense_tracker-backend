package com.expensetracker.smart_expense_tracker.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.expensetracker.smart_expense_tracker.dto.ExpenseRequest;
import com.expensetracker.smart_expense_tracker.model.Expense;
import com.expensetracker.smart_expense_tracker.model.User;
import com.expensetracker.smart_expense_tracker.repository.ExpenseRepo;
import com.expensetracker.smart_expense_tracker.repository.UserRepo;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepo expenseRepo;
    private final UserRepo userRepo;

    public ExpenseServiceImpl(ExpenseRepo expenseRepo,UserRepo userRepo){
        this.expenseRepo = expenseRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void addExpense(ExpenseRequest request, String email){

        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setDescription(request.getDescription());
        expense.setUser(user);

        expenseRepo.save(expense);

    }

    @Override
    public List<Expense> getExpenses(String email){

        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

        return expenseRepo.findByUser(user);
    }
}
