package com.expensetracker.smart_expense_tracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.expensetracker.smart_expense_tracker.model.Expense;
import com.expensetracker.smart_expense_tracker.model.User;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Long>{

    Page<Expense> findByUser(User user, Pageable pageable);

    Optional<Expense> findByIdAndUser(long id, User user);

    Page<Expense> findByUserAndDateBetween(User user, LocalDate starDate, LocalDate endDate,Pageable pageable);

    Page<Expense> findByUserAndCategory(User user, String category,Pageable pageable);

    @Query("""
            SELECT COALESCE(SUM(e.amount),0),COUNT(e)
            FROM Expense e
            WHERE e.user=:user 
                AND FUNCTION('YEAR',e.date)=:year
                AND FUNCTION('MONTH',e.date)=:month
    """)
    Object getMonthlySummary(@Param("user") User user,@Param("year") int year,@Param("month") int month);

    @Query("""
            SELECT COALESCE(SUM(e.amount),0),COUNT(e)
            FROM Expense e
            WHERE e.user=:user
                AND FUNCTION('YEAR',e.date)=:year
    """)
    Object getYearlySummary(@Param("user") User user, @Param("year") int year);

    @Query("""
            SELECT e.category,COALESCE(SUM(e.amount),0)
            FROM Expense e
            WHERE e.user=:user
            GROUP BY e.category
            """)
    List<Object[]> getCategorySummary(@Param("user") User user);

    @Query("""
            SELECT e.date,COALESCE(SUM(e.amount),0)
            FROM Expense e
            WHERE e.user=:user
            GROUP BY e.date
            """)
    List<Object[]> getDailySummary(@Param("user") User user);

    
}