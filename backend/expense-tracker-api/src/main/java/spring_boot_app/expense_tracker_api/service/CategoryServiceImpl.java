package spring_boot_app.expense_tracker_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_boot_app.expense_tracker_api.dto.CategoryDTO;
import spring_boot_app.expense_tracker_api.dto.UserDTO;
import spring_boot_app.expense_tracker_api.entity.CategoryEntity;
import spring_boot_app.expense_tracker_api.entity.User;
import spring_boot_app.expense_tracker_api.exceptions.ResourceAlreadyExistsException;
import spring_boot_app.expense_tracker_api.exceptions.ResourceNotFoundException;
import spring_boot_app.expense_tracker_api.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> list = categoryRepository.findByUserId(userService.getLoggedInUser().getId());
        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        boolean isCategoryPresent = categoryRepository.existsByNameAndUserId(categoryDTO.getName(), userService.getLoggedInUser()
                .getId());

        if (isCategoryPresent) {
            throw new ResourceAlreadyExistsException("Category is already present for the name " + categoryDTO.getName());
        }

        CategoryEntity newCategoryEntity = mapToEntity(categoryDTO);
        newCategoryEntity = categoryRepository.save(newCategoryEntity);
        return mapToDTO(newCategoryEntity);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser()
                .getId(), categoryId);

        if (optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException("Category not found for the id " + categoryId);
        }

        categoryRepository.delete(optionalCategory.get());
    }

    private CategoryEntity mapToEntity(CategoryDTO categoryDTO) {
        return CategoryEntity.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .categoryId(UUID.randomUUID().toString())
                .user(userService.getLoggedInUser())
                .build();
    }

    private CategoryDTO mapToDTO(CategoryEntity categoryEntity) {
        return CategoryDTO.builder()
                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .categoryIcon(categoryEntity.getCategoryIcon())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .user(mapToUserDTO(categoryEntity.getUser()))
                .build();
    }

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
