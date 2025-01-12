package spring_boot_app.expense_tracker_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring_boot_app.expense_tracker_api.io.CategoryResponse;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDTO {
    private String name;
    private String description;
    private BigDecimal amount;
    private String categoryId;
    private Date date;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private CategoryResponse category;
    private CategoryDTO categoryDTO;
    private UserDTO userDTO;
    private String expenseId;
}
