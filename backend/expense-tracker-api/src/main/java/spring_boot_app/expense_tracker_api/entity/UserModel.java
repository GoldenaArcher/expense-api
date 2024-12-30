package spring_boot_app.expense_tracker_api.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull(message = "Email is required.")
    @Email(message = "Please enter a valid email.")
    private String email;

    @NotNull(message = "Password is required.")
    @Size(min = 5, message = "Password should be at least 5 characters long.")
    private String password;

    private Integer age = 0;
}
