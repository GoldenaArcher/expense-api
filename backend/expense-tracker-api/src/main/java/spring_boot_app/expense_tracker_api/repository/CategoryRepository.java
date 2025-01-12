package spring_boot_app.expense_tracker_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot_app.expense_tracker_api.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByUserId(Long userId);

    Optional<CategoryEntity> findByUserIdAndCategoryId(Long id, String categoryId);

    Optional<CategoryEntity> findByNameAndUserId(String name, Long userId);

    boolean existsByNameAndUserId(String name, Long userId);
}
