package spring_boot_app.expense_tracker_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import spring_boot_app.expense_tracker_api.dto.ExpenseDTO;
import spring_boot_app.expense_tracker_api.entity.ExpenseEntity;
import spring_boot_app.expense_tracker_api.io.ExpenseRequest;
import spring_boot_app.expense_tracker_api.io.ExpenseResponse;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(target = "category", source = "expenseDTO.categoryDTO")
    ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO);

    ExpenseDTO mapToExpenseDTO(ExpenseRequest request);

    @Mapping(target = "categoryDTO", source = "expenseEntity.category")
    ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity);

    ExpenseEntity mapToExpenseEntity(ExpenseDTO expenseDTO);
}
