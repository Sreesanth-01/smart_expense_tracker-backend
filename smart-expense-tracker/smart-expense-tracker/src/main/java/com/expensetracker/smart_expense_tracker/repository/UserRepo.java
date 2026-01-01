package com.expensetracker.smart_expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expensetracker.smart_expense_tracker.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);
    
}
