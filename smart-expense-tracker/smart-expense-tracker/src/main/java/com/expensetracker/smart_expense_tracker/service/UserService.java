package com.expensetracker.smart_expense_tracker.service;

import com.expensetracker.smart_expense_tracker.dto.LoginRequest;
import com.expensetracker.smart_expense_tracker.dto.SignUpRequest;

public interface UserService {
    void registerUser(SignUpRequest signUpRequest);

    String login(LoginRequest loginRequest);
}
