package com.expensetracker.smart_expense_tracker.service;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.expensetracker.smart_expense_tracker.dto.CategorySummaryResponse;
import com.expensetracker.smart_expense_tracker.dto.ExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.MonthlySummaryResponse;
import com.expensetracker.smart_expense_tracker.dto.UpdateExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.YearlySummaryResponse;
import com.expensetracker.smart_expense_tracker.exception.ResourceNotFoundException;
import com.expensetracker.smart_expense_tracker.model.Expense;
import com.expensetracker.smart_expense_tracker.model.User;
import com.expensetracker.smart_expense_tracker.repository.ExpenseRepo;
import com.expensetracker.smart_expense_tracker.repository.UserRepo;



@Service
public class ExpenseServiceImpl implements ExpenseService {
    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);


    private final ExpenseRepo expenseRepo;
    private final UserRepo userRepo;

    public ExpenseServiceImpl(ExpenseRepo expenseRepo,UserRepo userRepo){
        this.expenseRepo = expenseRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void addExpense(ExpenseRequest request, String email){

        log.info("Adding expense for user: {}",email);

        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setDescription(request.getDescription());
        expense.setUser(user);

        expenseRepo.save(expense);
        log.info("Expense added successfully for user: {}",email);

    }

    @Override
    public Page<Expense> getExpenses(String email,Pageable pageable){

        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        return expenseRepo.findByUser(user,pageable);
    }

    @Override
    public Expense updateExpense(long id,String email,UpdateExpenseRequest request){
        log.info("Updating expense id {} for user {}",id,email);

        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        Expense expense = expenseRepo.findByIdAndUser(id,user).orElseThrow(()-> new ResourceNotFoundException("Expense not found"));

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

        Expense updatedExpense = expenseRepo.save(expense);
        log.info("Expense id {} updated successfully for user {}",id,email);

        return updatedExpense;

    }

    @Override
    public void deleteExpense(long id, String email){
        log.info("Deleting expense id {} for user {}",id,email);

        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        Expense expense = expenseRepo.findByIdAndUser(id, user).orElseThrow(()-> new ResourceNotFoundException("Expense not found"));

        expenseRepo.delete(expense);

        log.info("Expense id {} deleted successfully for user {}",id,email);
    }

    @Override
    public List<Expense> getExpensesByDateRange(String email,LocalDate startDate,LocalDate endDate){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        List<Expense> list = expenseRepo.findByUserAndDateBetween(user,startDate,endDate);

        return list;
    }

    @Override
    public List<Expense> getExpensesByCategory(String email,String category){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        List<Expense> list = expenseRepo.findByUserAndCategory(user,category);

        return list;
    }

    @Override
    public MonthlySummaryResponse getMonthlySummary(String email, int year, int month){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        Object[] monthlySummary =(Object[]) expenseRepo.getMonthlySummary(user, year, month);

        double totalAmount = ((Number) monthlySummary[0]).doubleValue();
        long totalTransactions = ((Number) monthlySummary[1]).longValue();

        double averageDaily = totalTransactions == 0
            ? 0.0
            : totalAmount/30;

        return new MonthlySummaryResponse(totalAmount, totalTransactions, averageDaily);

    }

    @Override
    public YearlySummaryResponse getYearlySummary(String email, int year){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        Object[] yearlySummary = (Object[]) expenseRepo.getYearlySummary(user, year);

        double totalAmount = ((Number) yearlySummary[0]).doubleValue();
        long totalTransactions = ((Number) yearlySummary[1]).longValue();

        double averageMonthly = totalTransactions == 0
            ? 0.0
            : totalAmount/12;
        
            return new YearlySummaryResponse(totalAmount, totalTransactions, averageMonthly);
    }

    @Override
    public List<CategorySummaryResponse> getCategorySummary(String email){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        List<Object[]> rows = expenseRepo.getCategorySummary(user);

        return rows.stream()                                //stream() is modern version of for each loop.
                .map(row -> new CategorySummaryResponse(    //map() converts each value into something else.
                    (String) row[0],                        //here,each row is converted to a CategorySummaryResponse dto.
                    ((Number) row[1]).doubleValue()
                )).toList();
    }
}
