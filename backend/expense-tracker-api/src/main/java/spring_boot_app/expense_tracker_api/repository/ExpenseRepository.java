package spring_boot_app.expense_tracker_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot_app.expense_tracker_api.entity.ExpenseEntity;

import java.sql.Date;
import java.util.Optional;

// for more details: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    // // SELECT * FROM tbl_expenses WHERE category=?
    // Page<Expense> findByCategory(String category, Pageable page);

    // // SELECT * FROM tbl_expenses WHERE name LIKE '%keyword%'
    // Page<Expense> findByNameContaining(String keyword, Pageable page);

    // // SELECT * FROM tbl_expenses WHERE date BETWEEN 'startDate AND 'endDate'
    // Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND category=?
    Page<ExpenseEntity> findByUserIdAndCategory(Long userId, String category, Pageable page);

    Page<ExpenseEntity> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND name LIKE '%keyword%'
    Page<ExpenseEntity> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND date BETWEEN 'startDate AND 'endDate'
    Page<ExpenseEntity> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=?
    Page<ExpenseEntity> findByUserId(Long userId, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND id=?
    Optional<ExpenseEntity> findByUserIdAndExpenseId(Long userId, String expenseId);
}
