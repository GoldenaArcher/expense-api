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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseRequest {
    @NotBlank(message = "Expense name must not be empty.")
    @Size(min = 3, message = "Expense name must be at least 3 characters.")
    private String name;

    private String description;

    @NotNull(message = "Expense amount must not be empty.")
    private BigDecimal amount;

    @NotBlank(message = "Expense category must not be empty.")
    private String categoryId;

    @NotNull(message = "Expense date must not be empty.")
    private Date date;
}
