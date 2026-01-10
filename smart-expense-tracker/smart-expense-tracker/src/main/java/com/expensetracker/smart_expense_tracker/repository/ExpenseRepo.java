package com.expensetracker.smart_expense_tracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expensetracker.smart_expense_tracker.model.Expense;
import com.expensetracker.smart_expense_tracker.model.User;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Long>{

    Page<Expense> findByUser(User user, Pageable pageable);

    Optional<Expense> findByIdAndUser(long id, User user);

    List<Expense> findByUserAndDateBetween(User user, LocalDate starDate, LocalDate endDate);

    List<Expense> findByUserAndCategory(User user, String category);

    
}
