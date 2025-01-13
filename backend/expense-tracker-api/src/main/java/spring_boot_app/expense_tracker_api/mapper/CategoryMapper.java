package spring_boot_app.expense_tracker_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import spring_boot_app.expense_tracker_api.dto.CategoryDTO;
import spring_boot_app.expense_tracker_api.entity.CategoryEntity;
import spring_boot_app.expense_tracker_api.io.CategoryRequest;
import spring_boot_app.expense_tracker_api.io.CategoryResponse;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryEntity mapToCategoryEntity(CategoryDTO categoryDTO);

    CategoryDTO mapToCategoryDTO(CategoryEntity categoryEntity);

    @Mapping(target = "categoryIcon", source = "categoryRequest.icon")
    CategoryDTO mapToCategoryDTO(CategoryRequest categoryRequest);

    CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO);
}
