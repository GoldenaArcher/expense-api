package spring_boot_app.expense_tracker_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring_boot_app.expense_tracker_api.entity.Expense;
import spring_boot_app.expense_tracker_api.exceptions.ResourceNotFoundException;
import spring_boot_app.expense_tracker_api.repository.ExpenseRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepo;

    @Autowired
    private UserService userService;

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);

        if (expense.isPresent()) {
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found for the id " + id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        try {
            expenseRepo.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Expense not found for ID: " + id);
        }
    }

    @Override
    public Expense saveExpenseDetails(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepo.save(expense);
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);

        Optional.ofNullable(expense.getName()).ifPresent(existingExpense::setName);
        Optional.ofNullable(expense.getDescription()).ifPresent(existingExpense::setDescription);
        Optional.ofNullable(expense.getCategory()).ifPresent(existingExpense::setCategory);
        Optional.ofNullable(expense.getAmount()).ifPresent(existingExpense::setAmount);
        Optional.ofNullable(expense.getDate()).ifPresent(existingExpense::setDate);

        return expenseRepo.save(existingExpense);
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable page) {
        return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
    }

    @Override
    public List<Expense> readByName(String name, Pageable page) {
        return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), name, page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
        if (startDate == null) {
            startDate = new Date(0);
        }

        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }

        return expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page)
                .toList();
    }
}
