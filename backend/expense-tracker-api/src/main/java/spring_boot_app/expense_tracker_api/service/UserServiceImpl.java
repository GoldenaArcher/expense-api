package spring_boot_app.expense_tracker_api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_boot_app.expense_tracker_api.entity.User;
import spring_boot_app.expense_tracker_api.entity.UserModel;
import spring_boot_app.expense_tracker_api.exceptions.ResourceAlreadyExistsException;
import spring_boot_app.expense_tracker_api.exceptions.ResourceNotFoundException;
import spring_boot_app.expense_tracker_api.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(UserModel user) {
        String userEmail = user.getEmail();

        if (userRepository.existsByEmail(userEmail)) {
            throw new ResourceAlreadyExistsException("User with email " + userEmail + " has already exists.");
        }

        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        return userRepository.save(newUser);
    }

    @Override
    public User readUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for the id: " + id));
    }

    @Override
    public User updateUser(UserModel user, Long id) {
        User existingUser = readUser(id);

        Optional.ofNullable(user.getName()).ifPresent(existingUser::setName);
        Optional.ofNullable(user.getAge()).ifPresent(existingUser::setAge);
        Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(user.getPassword()).ifPresent(existingUser::setPassword);

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(Long id) {
        User user = readUser(id);
        userRepository.delete(user);
    }
}
