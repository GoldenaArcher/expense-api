package spring_boot_app.expense_tracker_api.service;

import spring_boot_app.expense_tracker_api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    void deleteCategory(String categoryId);
}
