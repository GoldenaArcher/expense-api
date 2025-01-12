package spring_boot_app.expense_tracker_api.io;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseResponse {
    private String name;
    private String description;
    private BigDecimal amount;
    private String categoryId;
    private String expenseId;
    private Date date;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private CategoryResponse category;
}
