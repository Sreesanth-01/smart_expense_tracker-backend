package com.expensetracker.smart_expense_tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.smart_expense_tracker.dto.ExpenseRequest;
import com.expensetracker.smart_expense_tracker.model.Expense;
import com.expensetracker.smart_expense_tracker.service.ExpenseServiceImpl;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    
    private final ExpenseServiceImpl expenseService;

    public ExpenseController(ExpenseServiceImpl expenseService){
        this.expenseService=expenseService;
    }

    @PostMapping
    public ResponseEntity<String> addExpense(@RequestBody ExpenseRequest request){

        String email =(String) SecurityContextHolder    //This gets the email from JWT to add/display expenses of corresponding user
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

        expenseService.addExpense(request, email);
        return ResponseEntity.ok("Expense added successfully");
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(){

         String email =(String) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();
        
        List<Expense> list = expenseService.getExpenses(email);
        return ResponseEntity.ok(list);
    }
}
