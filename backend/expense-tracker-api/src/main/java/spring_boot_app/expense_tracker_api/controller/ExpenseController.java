package spring_boot_app.expense_tracker_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring_boot_app.expense_tracker_api.dto.CategoryDTO;
import spring_boot_app.expense_tracker_api.dto.ExpenseDTO;
import spring_boot_app.expense_tracker_api.entity.Expense;
import spring_boot_app.expense_tracker_api.io.CategoryResponse;
import spring_boot_app.expense_tracker_api.io.ExpenseRequest;
import spring_boot_app.expense_tracker_api.io.ExpenseResponse;
import spring_boot_app.expense_tracker_api.service.ExpenseService;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getAllExpenses(Pageable page) {
        List<ExpenseDTO> expenseDTOList = expenseService.getAllExpenses(page);
        return expenseDTOList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @GetMapping("/expenses/{id}")
    public ExpenseResponse getExpenseById(@PathVariable("id") String expenseId) {
        return mapToResponse(expenseService.getExpenseById(expenseId));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam("expenseId") String expenseId) {
        expenseService.deleteExpenseById(expenseId);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
        ExpenseDTO expenseDTO = mapToDTO(expenseRequest);
        expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
        return mapToResponse(expenseDTO);
    }

    private ExpenseResponse mapToResponse(ExpenseDTO expenseDTO) {
        return ExpenseResponse.builder()
                .expenseId(expenseDTO.getExpenseId())
                .name(expenseDTO.getName())
                .description(expenseDTO.getDescription())
                .amount(expenseDTO.getAmount())
                .date(expenseDTO.getDate())
                .category(mapToCategoryResponse(expenseDTO.getCategoryDTO()))
                .createdAt(expenseDTO.getCreatedAt())
                .updatedAt(expenseDTO.getUpdatedAt())
                .build();
    }

    private CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .build();
    }

    private ExpenseDTO mapToDTO(ExpenseRequest expenseRequest) {
        return ExpenseDTO.builder()
                .name(expenseRequest.getName())
                .description(expenseRequest.getDescription())
                .amount(expenseRequest.getAmount())
                .date(expenseRequest.getDate())
                .categoryId(expenseRequest.getCategoryId())
                .build();
    }

    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest expenseRequest, @PathVariable String expenseId) {
        ExpenseDTO updatedExpense = mapToDTO(expenseRequest);
        return mapToResponse(expenseService.updateExpenseDetails(expenseId, updatedExpense));
    }

    @GetMapping("/expenses/category")
    public List<ExpenseResponse> getExpensesByCategory(@RequestParam String category, Pageable page) {
        return expenseService.readByCategory(category, page).stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/expenses/name")
    public List<ExpenseResponse> getExpensesByName(@RequestParam String name, Pageable page) {
        List<ExpenseDTO> list = expenseService.readByName(name, page);
        return list.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @GetMapping("/expenses/date")
    public List<ExpenseResponse> getExpensesByName(@RequestParam(required = false) Date startDate,
                                                   @RequestParam(required = false) Date endDate,
                                                   Pageable page) {
        return expenseService.readByDate(startDate, endDate, page).stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
