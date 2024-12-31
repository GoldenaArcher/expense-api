package spring_boot_app.expense_tracker_api.entity;

import lombok.Data;

@Data
public class AuthModel {
    private String email;

    private String password;
}
