package com.expensetracker.smart_expense_tracker.service;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.expensetracker.smart_expense_tracker.dto.ExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.MonthlySummaryResponse;
import com.expensetracker.smart_expense_tracker.dto.UpdateExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.YearlySummaryResponse;
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
    public Page<Expense> getExpenses(String email,Pageable pageable){

        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

        return expenseRepo.findByUser(user,pageable);
    }

    @Override
    public Expense updateExpense(long id,String email,UpdateExpenseRequest request){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        Expense expense = expenseRepo.findByIdAndUser(id,user).orElseThrow(()-> new RuntimeException("Expense not found"));

        if(request.getAmount() != null){
            expense.setAmount(request.getAmount());
        }
        if(request.getCategory()!=null){
            expense.setCategory(request.getCategory());
        }
        if(request.getDate()!=null){
            expense.setDate(request.getDate());
        }
        if(request.getDescription()!=null){
            expense.setDescription(request.getDescription());
        }

        return expenseRepo.save(expense);
    }

    @Override
    public void deleteExpense(long id, String email){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        Expense expense = expenseRepo.findByIdAndUser(id, user).orElseThrow(()-> new RuntimeException("User not found"));

        expenseRepo.delete(expense);
    }

    @Override
    public List<Expense> getExpensesByDateRange(String email,LocalDate startDate,LocalDate endDate){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        List<Expense> list = expenseRepo.findByUserAndDateBetween(user,startDate,endDate);

        return list;
    }

    @Override
    public List<Expense> getExpensesByCategory(String email,String category){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        List<Expense> list = expenseRepo.findByUserAndCategory(user,category);

        return list;
    }

    @Override
    public MonthlySummaryResponse getMonthlySummary(String email, int year, int month){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        Object[] monthlySummary =(Object[]) expenseRepo.getMothlySummary(user, year, month);

        double totalAmount = ((Number) monthlySummary[0]).doubleValue();
        long totalTransactions = ((Number) monthlySummary[1]).longValue();

        double averageDaily = totalTransactions == 0
            ? 0.0
            : totalAmount/30;

        return new MonthlySummaryResponse(totalAmount, totalTransactions, averageDaily);

    }

    @Override
    public YearlySummaryResponse getYearlySummary(String email, int year){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        Object[] yearlySummary = (Object[]) expenseRepo.getYearlySummary(user, year);

        double totalAmount = ((Number) yearlySummary[0]).doubleValue();
        long totalTransactions = ((Number) yearlySummary[1]).longValue();

        double averageMonthly = totalTransactions == 0
            ? 0.0
            : totalAmount/12;
        
            return new YearlySummaryResponse(totalAmount, totalTransactions, averageMonthly);
    }
}
