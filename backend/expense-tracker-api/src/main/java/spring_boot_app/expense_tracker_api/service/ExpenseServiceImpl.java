package spring_boot_app.expense_tracker_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring_boot_app.expense_tracker_api.dto.CategoryDTO;
import spring_boot_app.expense_tracker_api.dto.ExpenseDTO;
import spring_boot_app.expense_tracker_api.entity.CategoryEntity;
import spring_boot_app.expense_tracker_api.entity.ExpenseEntity;
import spring_boot_app.expense_tracker_api.exceptions.ResourceNotFoundException;
import spring_boot_app.expense_tracker_api.mapper.ExpenseMapper;
import spring_boot_app.expense_tracker_api.repository.CategoryRepository;
import spring_boot_app.expense_tracker_api.repository.ExpenseRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepo;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseDTO> getAllExpenses(Pageable page) {
        List<ExpenseEntity> expenseList = expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page).toList();
        return expenseList.stream().map(expenseMapper::mapToExpenseDTO).collect(Collectors.toList());
    }

    @Override
    public ExpenseDTO getExpenseById(String expenseId) {
        ExpenseEntity existingExpense = getExpenseEntity(expenseId);
        return expenseMapper.mapToExpenseDTO(existingExpense);
    }

    private ExpenseEntity getExpenseEntity(String expenseId) {
        Optional<ExpenseEntity> expense = expenseRepo.findByUserIdAndExpenseId(userService.getLoggedInUser().getId(), expenseId);

        if (expense.isEmpty()) {
            throw new ResourceNotFoundException("Expense is not found for the id " + expenseId);
        }
        return expense.get();
    }

    @Override
    public void deleteExpenseById(String expenseId) {
        ExpenseEntity expense = getExpenseEntity(expenseId);
        expenseRepo.delete(expense);
    }

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        // check the existence of category
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser()
                .getId(), expenseDTO.getCategoryId());
        if (optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException("Category not found for the id " + expenseDTO.getCategoryId());
        }

        expenseDTO.setExpenseId(UUID.randomUUID().toString());

        // map to entity object
        ExpenseEntity newExpense = expenseMapper.mapToExpenseEntity(expenseDTO);
        newExpense.setCategory(optionalCategory.get());
        newExpense.setUser(userService.getLoggedInUser());
        newExpense = expenseRepo.save(newExpense);

        return expenseMapper.mapToExpenseDTO(newExpense);
    }

    @Override
    public ExpenseDTO updateExpenseDetails(String expenseId, ExpenseDTO expenseDTO) {
        ExpenseEntity existingExpense = getExpenseEntity(expenseId);

        if (expenseDTO.getCategoryId() != null) {
            String categoryId = expenseDTO.getCategoryId();
            Optional<CategoryEntity> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser()
                    .getId(), categoryId);
            if (optionalCategory.isEmpty()) {
                throw new ResourceNotFoundException("Category not found for the id" + categoryId);
            }
            existingExpense.setCategory(optionalCategory.get());
        }

        Optional.ofNullable(expenseDTO.getName()).ifPresent(existingExpense::setName);
        Optional.ofNullable(expenseDTO.getDescription()).ifPresent(existingExpense::setDescription);
        Optional.ofNullable(expenseDTO.getAmount()).ifPresent(existingExpense::setAmount);
        Optional.ofNullable(expenseDTO.getDate()).ifPresent(existingExpense::setDate);
        existingExpense = expenseRepo.save(existingExpense);

        return expenseMapper.mapToExpenseDTO(existingExpense);
    }

    @Override
    public List<ExpenseDTO> readByCategory(String category, Pageable page) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByNameAndUserId(category, userService.getLoggedInUser()
                .getId());
        if (optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException("Category not found for the name " + category);
        }

        return expenseRepo.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), optionalCategory.get()
                .getId(), page).toList().stream().map(expenseMapper::mapToExpenseDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> readByName(String name, Pageable page) {
        List <ExpenseEntity> list = expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), name, page).toList();
        return list.stream().map(expenseMapper::mapToExpenseDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> readByDate(Date startDate, Date endDate, Pageable page) {
        if (startDate == null) {
            startDate = new Date(0);
        }

        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }

        return expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page)
                .toList().stream().map(expenseMapper::mapToExpenseDTO).collect(Collectors.toList());
    }
}
