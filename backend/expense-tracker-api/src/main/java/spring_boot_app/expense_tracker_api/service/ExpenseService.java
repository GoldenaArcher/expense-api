package spring_boot_app.expense_tracker_api.service;

import org.springframework.data.domain.Pageable;
import spring_boot_app.expense_tracker_api.dto.ExpenseDTO;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpenses(Pageable page);

    ExpenseDTO getExpenseById(String expenseId);

    void deleteExpenseById(String id);

    ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO);

    ExpenseDTO updateExpenseDetails(String id, ExpenseDTO expenseDTO);

    List<ExpenseDTO> readByCategory(String category, Pageable page);

    List<ExpenseDTO> readByName(String name, Pageable page);

    List<ExpenseDTO> readByDate(Date startDate, Date endDate, Pageable page);
}
