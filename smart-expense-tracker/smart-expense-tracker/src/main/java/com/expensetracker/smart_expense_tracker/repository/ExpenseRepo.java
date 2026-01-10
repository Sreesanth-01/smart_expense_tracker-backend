package com.expensetracker.smart_expense_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expensetracker.smart_expense_tracker.model.Expense;
import com.expensetracker.smart_expense_tracker.model.User;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Long>{

    List<Expense> findByUser(User user);

    Optional<Expense> findByIdAndUser(long id, User user);

    
}
