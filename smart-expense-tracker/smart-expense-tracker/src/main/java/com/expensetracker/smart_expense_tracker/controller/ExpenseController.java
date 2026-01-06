package com.expensetracker.smart_expense_tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    
    @GetMapping
    public String testSecureEndPoint(){
        return "JWT secured endpoint works!";
    }
}
