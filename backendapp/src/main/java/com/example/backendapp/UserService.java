package com.example.backendapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("User with id: %s does not exist", id)
            );
        }
        return mapToUserResponse(user.get());
    }

    public UserResponse getUserByUserName(String userName) {
        var user = userRepository.findByUserName(userName);
        if (user.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("User with id: %s does not exist", userName)
            );
        }
        return mapToUserResponse(user.get());
    }

    public UserResponse createUser(User user) {
        String firstNameThreeChars = user.getFirstName().substring(0, Math.min(user.getFirstName().length(), 3));
        String lastNameThreeChars = user.getLastName().substring(0, Math.min(user.getLastName().length(), 3));

        // verify & get updated userName if it already exists
        String createdUserName = createUserNameAfterValidation(firstNameThreeChars.concat(lastNameThreeChars));
        user.setUserName(createdUserName);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActiveStatus(Objects.requireNonNullElse(user.getActiveStatus(), false));

        var createdUser = userRepository.save(user);

        return mapToUserResponse(createdUser);
    }

    public UserResponse updateUser(Long id, User updateUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User with given ID does not exist, please create the user first"
            );
        }

        var existingUser = existingUserOptional.get();
        List<User> userRepos = userRepository.findAll();
        var isUserNameExistsInRequest = updateUser.getUserName() != null;

        if (isUserNameExistsInRequest) {
            var existingUserNames = userRepos.stream()
                    .filter(user -> !user.getId().equals(id))
                    .map(User::getUserName)
                    .collect(Collectors.toUnmodifiableSet());

            if (existingUserNames.contains(updateUser.getUserName())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "User with the given user name already exists"
                );
            }

            existingUser.setUserName(updateUser.getUserName());
        }

        existingUser.setFirstName(updateUser.getFirstName());
        existingUser.setLastName(updateUser.getLastName());
        existingUser.setEmail(updateUser.getEmail());
        existingUser.setRole(updateUser.getRole());

        if (updateUser.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }

        if (updateUser.getActiveStatus() != null) {
            existingUser.setActiveStatus(updateUser.getActiveStatus());
        }

        var updatedUser = userRepository.save(existingUser);
        return mapToUserResponse(updatedUser);
    }


    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private String createUserNameAfterValidation(String createdUserName) {
        var existingUsers = userRepository.findAll();
        var existingUserNames = existingUsers.stream().map(User::getUserName).collect(Collectors.toUnmodifiableSet());

        if (!existingUserNames.contains(createdUserName)) {
            return createdUserName;
        }
        // creates a userName with integer prefix, if it already exists
        for (int i = 1; i <= 100; i++) {
            int prefixForUserName = i;
            if (!existingUserNames.contains(createdUserName + prefixForUserName)) {
                return createdUserName + prefixForUserName;
            }
        }

        return createdUserName;
    }

    private UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getActiveStatus()
        );
    }
}
