package com.expensetracker.smart_expense_tracker.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailySummaryResponse {
    private LocalDate date;
    private double totalAmount;
}
