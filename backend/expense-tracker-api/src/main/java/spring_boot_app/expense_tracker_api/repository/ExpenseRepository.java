package spring_boot_app.expense_tracker_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot_app.expense_tracker_api.entity.Expense;

import java.sql.Date;
import java.util.Optional;

// for more details: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // // SELECT * FROM tbl_expenses WHERE category=?
    // Page<Expense> findByCategory(String category, Pageable page);

    // // SELECT * FROM tbl_expenses WHERE name LIKE '%keyword%'
    // Page<Expense> findByNameContaining(String keyword, Pageable page);

    // // SELECT * FROM tbl_expenses WHERE date BETWEEN 'startDate AND 'endDate'
    // Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND category=?
    Page<Expense> findByUserIdAndCategory(Long userId, String category, Pageable page);

    Page<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND name LIKE '%keyword%'
    Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND date BETWEEN 'startDate AND 'endDate'
    Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=?
    Page<Expense> findByUserId(Long userId, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND id=?
    Optional<Expense> findByUserIdAndExpenseId(Long userId, String expenseId);
}
