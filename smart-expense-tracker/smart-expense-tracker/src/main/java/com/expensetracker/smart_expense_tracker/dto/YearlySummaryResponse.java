package com.expensetracker.smart_expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlySummaryResponse {
    private double totalAmount;
    private long totalTransactions;
    private double monthlyAverage;
}
