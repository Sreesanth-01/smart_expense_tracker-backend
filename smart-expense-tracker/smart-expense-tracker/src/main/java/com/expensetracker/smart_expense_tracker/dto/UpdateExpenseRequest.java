package com.expensetracker.smart_expense_tracker.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UpdateExpenseRequest {
    private Double amount;
    private String category;
    private LocalDate date;
    private String description;
}
