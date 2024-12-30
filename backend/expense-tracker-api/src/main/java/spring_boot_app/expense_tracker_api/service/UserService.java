package spring_boot_app.expense_tracker_api.service;

import spring_boot_app.expense_tracker_api.entity.User;
import spring_boot_app.expense_tracker_api.entity.UserModel;

public interface UserService {
    User createUser(UserModel user);

    User readUser(Long id);

    User updateUser(UserModel user, Long id);

    void delete(Long id);
}
