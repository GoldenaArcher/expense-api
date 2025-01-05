package spring_boot_app.expense_tracker_api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String jwtToken;
}
