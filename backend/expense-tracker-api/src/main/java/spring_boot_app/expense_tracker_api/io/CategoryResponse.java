package spring_boot_app.expense_tracker_api.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private String name;
    private String description;
    private String categoryIcon;
    private String categoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
