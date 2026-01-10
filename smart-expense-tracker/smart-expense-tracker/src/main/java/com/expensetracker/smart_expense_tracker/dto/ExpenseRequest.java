package com.expensetracker.smart_expense_tracker.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExpenseRequest {
    @NotBlank(message = "Amount is required")
    private double amount;
    @NotBlank(message = "Category is required")
    private String category;
    @NotBlank(message = "date is required")
    private LocalDate date;

    private String description;
}
