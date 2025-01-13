package spring_boot_app.expense_tracker_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring_boot_app.expense_tracker_api.dto.CategoryDTO;
import spring_boot_app.expense_tracker_api.dto.ExpenseDTO;
import spring_boot_app.expense_tracker_api.io.CategoryResponse;
import spring_boot_app.expense_tracker_api.io.ExpenseRequest;
import spring_boot_app.expense_tracker_api.io.ExpenseResponse;
import spring_boot_app.expense_tracker_api.mapper.ExpenseMapper;
import spring_boot_app.expense_tracker_api.service.ExpenseService;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseMapper expenseMapper;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getAllExpenses(Pageable page) {
        List<ExpenseDTO> expenseDTOList = expenseService.getAllExpenses(page);
        return expenseDTOList.stream().map(expenseMapper::mapToExpenseResponse).collect(Collectors.toList());
    }

    @GetMapping("/expenses/{id}")
    public ExpenseResponse getExpenseById(@PathVariable("id") String expenseId) {
        return expenseMapper.mapToExpenseResponse(expenseService.getExpenseById(expenseId));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam("expenseId") String expenseId) {
        expenseService.deleteExpenseById(expenseId);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
        ExpenseDTO expenseDTO = expenseMapper.mapToExpenseDTO(expenseRequest);
        expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
        return expenseMapper.mapToExpenseResponse(expenseDTO);
    }

    private CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .build();
    }

    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest expenseRequest, @PathVariable String expenseId) {
        ExpenseDTO updatedExpense = expenseMapper.mapToExpenseDTO(expenseRequest);
        System.out.println(updatedExpense);
        return expenseMapper.mapToExpenseResponse(expenseService.updateExpenseDetails(expenseId, updatedExpense));
    }

    @GetMapping("/expenses/category")
    public List<ExpenseResponse> getExpensesByCategory(@RequestParam String category, Pageable page) {
        return expenseService.readByCategory(category, page).stream().map(expenseMapper::mapToExpenseResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/expenses/name")
    public List<ExpenseResponse> getExpensesByName(@RequestParam String name, Pageable page) {
        List<ExpenseDTO> list = expenseService.readByName(name, page);
        return list.stream().map(expenseMapper::mapToExpenseResponse).collect(Collectors.toList());
    }

    @GetMapping("/expenses/date")
    public List<ExpenseResponse> getExpensesByName(@RequestParam(required = false) Date startDate,
                                                   @RequestParam(required = false) Date endDate,
                                                   Pageable page) {
        return expenseService.readByDate(startDate, endDate, page).stream().map(expenseMapper::mapToExpenseResponse)
                .collect(Collectors.toList());
    }
}
