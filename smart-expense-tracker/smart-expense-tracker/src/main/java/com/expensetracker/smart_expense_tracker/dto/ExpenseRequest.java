package com.expensetracker.smart_expense_tracker.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ExpenseRequest {
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private double amount;
    @NotBlank(message = "Category is required")
    private String category;
    @NotNull(message = "Date is required")
    private LocalDate date;

    private String description;
}
