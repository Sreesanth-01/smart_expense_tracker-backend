package com.expensetracker.smart_expense_tracker.controller;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.expensetracker.smart_expense_tracker.dto.ExpenseRequest;
import com.expensetracker.smart_expense_tracker.dto.UpdateExpenseRequest;
import com.expensetracker.smart_expense_tracker.model.Expense;
import com.expensetracker.smart_expense_tracker.service.ExpenseServiceImpl;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    
    private final ExpenseServiceImpl expenseService;

    public ExpenseController(ExpenseServiceImpl expenseService){
        this.expenseService=expenseService;
    }

    @PostMapping
    public ResponseEntity<String> addExpense(@RequestBody ExpenseRequest request, @AuthenticationPrincipal String email){

        expenseService.addExpense(request, email);
        return ResponseEntity.ok("Expense added successfully");
    }

    @GetMapping
    public ResponseEntity<Page<Expense>> getExpenses(@AuthenticationPrincipal String email,Pageable pageable){

        
        Page<Expense> page = expenseService.getExpenses(email,pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateExpense(@PathVariable long id, @RequestBody UpdateExpenseRequest request, @AuthenticationPrincipal String email){
        expenseService.updateExpense(id, email, request);

        return ResponseEntity.ok("Update Successfull");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable long id,@AuthenticationPrincipal String email){
        expenseService.deleteExpense(id, email);

        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Expense>> getByDateRange(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate,@AuthenticationPrincipal String email){
        return ResponseEntity.ok(expenseService.getExpensesByDateRange(email, startDate, endDate));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getByCategory(@PathVariable String category, @AuthenticationPrincipal String email){
        return ResponseEntity.ok(expenseService.getExpensesByCategory(email, category));
    }
}
