package spring_boot_app.expense_tracker_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring_boot_app.expense_tracker_api.dto.CategoryDTO;
import spring_boot_app.expense_tracker_api.io.CategoryRequest;
import spring_boot_app.expense_tracker_api.io.CategoryResponse;
import spring_boot_app.expense_tracker_api.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = mapToDTO(categoryRequest);
        categoryDTO = categoryService.saveCategory(categoryDTO);
        return mapToResponse(categoryDTO);
    }

    @GetMapping
    public List<CategoryResponse> readCategories() {
        List<CategoryDTO> list = categoryService.getAllCategories();
        return list.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    private CategoryResponse mapToResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .createdAt(categoryDTO.getCreatedAt())
                .updatedAt(categoryDTO.getUpdatedAt())
                .build();
    }

    private CategoryDTO mapToDTO(CategoryRequest categoryRequest) {
        return CategoryDTO.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .categoryIcon(categoryRequest.getIcon())
                .build();
    }
}
