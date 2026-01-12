package com.expensetracker.smart_expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategorySummaryResponse {
    private String category;
    private double totalAmount;
}
