package com.expensetracker.smart_expense_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.smart_expense_tracker.dto.ApiResponse;
import com.expensetracker.smart_expense_tracker.dto.CategorySummaryResponse;
import com.expensetracker.smart_expense_tracker.dto.ExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.MonthlySummaryResponse;
import com.expensetracker.smart_expense_tracker.dto.UpdateExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.YearlySummaryResponse;
import com.expensetracker.smart_expense_tracker.model.Expense;
import com.expensetracker.smart_expense_tracker.service.ExpenseServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private static final Logger log = LoggerFactory.getLogger(ExpenseController.class);
    
    private final ExpenseServiceImpl expenseService;

    public ExpenseController(ExpenseServiceImpl expenseService){
        this.expenseService=expenseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addExpense(@Valid @RequestBody ExpenseRequest request, @AuthenticationPrincipal String email){
        log.info("Recieved POST/api/expenses request");
        expenseService.addExpense(request, email);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Expense added successfully", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Expense>>> getExpenses(@AuthenticationPrincipal String email,Pageable pageable){
        log.info("Recieved GET/api/expenses request");
        
        Page<Expense> page = expenseService.getExpenses(email,pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Expenses retrieved successfully", page));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateExpense(@PathVariable long id, @RequestBody UpdateExpenseRequest request, @AuthenticationPrincipal String email){
        log.info("Recieved PUT/api/expenses/{} request",id);
        
        expenseService.updateExpense(id, email, request);

        return ResponseEntity.ok(new ApiResponse<>(true, "Expense Updated successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(@PathVariable long id,@AuthenticationPrincipal String email){
        log.info("Recieved DELETE/api/expenses/{} request",id);
        
        expenseService.deleteExpense(id, email);

        return ResponseEntity.ok(new ApiResponse<>(true, "Expense Deleted successfully", null));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<Expense>>> getByDateRange(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate,@AuthenticationPrincipal String email){
        log.info("Recieved GET/api/expenses/date-range request");
        
        return ResponseEntity.ok(new ApiResponse<>(false, "Expenses retrieved successfully", expenseService.getExpensesByDateRange(email, startDate, endDate)));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Expense>>> getByCategory(@PathVariable String category, @AuthenticationPrincipal String email){
        log.info("Recieved GET/api/expenses/category/{} request",category);
        
        return ResponseEntity.ok(new ApiResponse<>(true, "Expenses retrieved successfully",expenseService.getExpensesByCategory(email, category)));
    }

    @GetMapping("/summary/monthly")
    public ResponseEntity<MonthlySummaryResponse> getMonthlySummary(@RequestParam int year, @RequestParam int month, @AuthenticationPrincipal String email){
        log.info("Recieved GET/api/expenses/summary/monthly request");
       
        return ResponseEntity.ok(expenseService.getMonthlySummary(email, year, month));
    }

    @GetMapping("/summary/yearly")
    public ResponseEntity<YearlySummaryResponse> getYearlySummary(@RequestParam int year, @AuthenticationPrincipal String email){
        log.info("Recieved GET/api/expenses/summary/yearly request");
        
        return ResponseEntity.ok(expenseService.getYearlySummary(email, year));
    }

    @GetMapping("/summary/category")
    public ResponseEntity<List<CategorySummaryResponse>> getCategorySummary(@AuthenticationPrincipal String email){
        log.info("Recieved GET/api/expenses/summary/category request");
        
        return ResponseEntity.ok(expenseService.getCategorySummary(email));
    }

    
}
