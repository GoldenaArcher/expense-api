package spring_boot_app.expense_tracker_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring_boot_app.expense_tracker_api.entity.User;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private String name;
    private String description;
    private String categoryIcon;
    private String categoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private UserDTO user;
}
