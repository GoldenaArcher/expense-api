package spring_boot_app.expense_tracker_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring_boot_app.expense_tracker_api.dto.CategoryDTO;
import spring_boot_app.expense_tracker_api.dto.ExpenseDTO;
import spring_boot_app.expense_tracker_api.entity.CategoryEntity;
import spring_boot_app.expense_tracker_api.entity.Expense;
import spring_boot_app.expense_tracker_api.exceptions.ResourceNotFoundException;
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

    @Override
    public List<ExpenseDTO> getAllExpenses(Pageable page) {
        List<Expense> expenseList = expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page).toList();
        return expenseList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ExpenseDTO getExpenseById(String expenseId) {
        Expense existingExpense = getExpenseEntity(expenseId);
        return mapToDTO(existingExpense);
    }

    private Expense getExpenseEntity(String expenseId) {
        Optional<Expense> expense = expenseRepo.findByUserIdAndExpenseId(userService.getLoggedInUser().getId(), expenseId);

        if (expense.isEmpty()) {
            throw new ResourceNotFoundException("Expense is not found for the id " + expenseId);
        }
        return expense.get();
    }

    @Override
    public void deleteExpenseById(String expenseId) {
        Expense expense = getExpenseEntity(expenseId);
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
        Expense newExpense = mapToEntity(expenseDTO);
        newExpense.setCategory(optionalCategory.get());
        newExpense.setUser(userService.getLoggedInUser());
        newExpense = expenseRepo.save(newExpense);

        return mapToDTO(newExpense);
    }

    private ExpenseDTO mapToDTO(Expense newExpense) {
        return ExpenseDTO.builder()
                .expenseId(newExpense.getExpenseId())
                .name(newExpense.getName())
                .description(newExpense.getDescription())
                .amount(newExpense.getAmount())
                .date(newExpense.getDate())
                .createdAt(newExpense.getCreatedAt())
                .updatedAt(newExpense.getUpdatedAt())
                .categoryDTO(mapToCategoryDTO(newExpense.getCategory()))
                .build();
    }

    private CategoryDTO mapToCategoryDTO(CategoryEntity category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .categoryId(category.getCategoryId())
                .build();
    }

    private Expense mapToEntity(ExpenseDTO expenseDTO) {
        return Expense.builder()
                .expenseId(expenseDTO.getExpenseId())
                .name(expenseDTO.getName())
                .description(expenseDTO.getDescription())
                .date(expenseDTO.getDate())
                .amount(expenseDTO.getAmount())
                .build();
    }

    @Override
    public ExpenseDTO updateExpenseDetails(String expenseId, ExpenseDTO expenseDTO) {
        Expense existingExpense = getExpenseEntity(expenseId);

        if (expenseDTO.getCategoryId() != null) {
            String categoryId = expenseDTO.getCategoryId();
            Optional<CategoryEntity> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser()
                    .getId(), categoryId);
            if (optionalCategory.isEmpty()) {
                throw new ResourceNotFoundException("Category not found for the id" + categoryId);
            }
            existingExpense.setCategory(optionalCategory.get());
        }

        Optional.ofNullable(existingExpense.getName()).ifPresent(existingExpense::setName);
        Optional.ofNullable(existingExpense.getDescription()).ifPresent(existingExpense::setDescription);
        Optional.ofNullable(existingExpense.getAmount()).ifPresent(existingExpense::setAmount);
        Optional.ofNullable(existingExpense.getDate()).ifPresent(existingExpense::setDate);
        existingExpense = expenseRepo.save(existingExpense);

        return mapToDTO(existingExpense);
    }

    @Override
    public List<ExpenseDTO> readByCategory(String category, Pageable page) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByNameAndUserId(category, userService.getLoggedInUser()
                .getId());
        if (optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException("Category not found for the name " + category);
        }

        return expenseRepo.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), optionalCategory.get()
                .getId(), page).toList().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> readByName(String name, Pageable page) {
        List <Expense> list = expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), name, page).toList();
        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
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
                .toList().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
