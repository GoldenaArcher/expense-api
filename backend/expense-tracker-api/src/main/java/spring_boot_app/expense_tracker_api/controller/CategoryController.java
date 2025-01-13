package spring_boot_app.expense_tracker_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring_boot_app.expense_tracker_api.dto.CategoryDTO;
import spring_boot_app.expense_tracker_api.io.CategoryRequest;
import spring_boot_app.expense_tracker_api.io.CategoryResponse;
import spring_boot_app.expense_tracker_api.mapper.CategoryMapper;
import spring_boot_app.expense_tracker_api.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = categoryMapper.mapToCategoryDTO(categoryRequest);
        categoryDTO = categoryService.saveCategory(categoryDTO);
        return categoryMapper.mapToCategoryResponse(categoryDTO);
    }

    @GetMapping
    public List<CategoryResponse> readCategories() {
        List<CategoryDTO> list = categoryService.getAllCategories();
        return list.stream().map(categoryMapper::mapToCategoryResponse).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
