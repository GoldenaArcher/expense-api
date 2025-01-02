package spring_boot_app.expense_tracker_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot_app.expense_tracker_api.entity.User;
import spring_boot_app.expense_tracker_api.entity.UserModel;
import spring_boot_app.expense_tracker_api.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> readUser() {
        return new ResponseEntity<>(userService.readUser(), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.delete();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
