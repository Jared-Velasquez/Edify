package com.jvel.edify.service;

import com.jvel.edify.controller.exceptions.UserAlreadyExistsException;
import com.jvel.edify.controller.exceptions.UserNotFoundException;
import com.jvel.edify.controller.responses.UserQueryMultipleResponse;
import com.jvel.edify.controller.responses.UserQueryResponse;
import com.jvel.edify.entity.User;
import com.jvel.edify.entity.enums.Gender;
import com.jvel.edify.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserQueryMultipleResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserQueryMultipleResponse.builder()
                .users(users)
                .build();
    }

    public UserQueryResponse getUserByEmailAddress(String emailAddress) {
        Optional<User> user = userRepository.findByEmailAddress(emailAddress);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found by email address " + emailAddress);

        return UserQueryResponse.builder()
                .user(user.get())
                .build();
    }

    public UserQueryResponse getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found by id " + userId);

        return UserQueryResponse.builder()
                .user(user.get())
                .build();
    }

    @Transactional
    public void updateEmail(Integer id, String newEmail) {
        if (id == null)
            throw new IllegalStateException("ID not specified");
        if (newEmail == null || newEmail.length() == 0)
            throw new IllegalStateException("Desired email not specified");

        Optional<User> userWithId = userRepository.findById(id);
        Optional<User> userNewEmail = userRepository.findByEmailAddress(newEmail);

        // If no student has this old email
        if (userWithId.isEmpty())
            throw new UserNotFoundException("User not found by id " + id);
        // If a student already has this new email
        if (userNewEmail.isPresent())
            throw new UserAlreadyExistsException("User already exists by email address " + newEmail);

        userWithId.get().setEmailAddress(newEmail);
    }

    @Transactional
    public void updateEmail(String oldEmail, String newEmail) {
        if (oldEmail == null || oldEmail.length() == 0)
            throw new IllegalStateException("Current email not specified");
        if (newEmail == null || newEmail.length() == 0)
            throw new IllegalStateException("Desired email not specified");

        Optional<User> userOldEmail = userRepository.findByEmailAddress(oldEmail);
        Optional<User> userNewEmail = userRepository.findByEmailAddress(newEmail);

        // If no student has this old email
        if (userOldEmail.isEmpty())
            throw new IllegalStateException("User not found by email address " + oldEmail);
        // If a student already has this new email
        if (userNewEmail.isPresent())
            throw new UserAlreadyExistsException("User already exists by email address " + newEmail);

        userOldEmail.get().setEmailAddress(newEmail);
    }

    @Transactional
    public void updateName(Integer studentId, String firstName, String lastName) {
        if (studentId == null)
            throw new IllegalStateException("Student ID not specified");

        Optional<User> user = userRepository.findById(studentId);

        // If first name is specified but is an empty string
        if (firstName != null && firstName.length() == 0)
            throw new IllegalStateException("First name cannot be empty");

        // If last name is specified but is an empty string
        if (lastName != null && lastName.length() == 0)
            throw new IllegalStateException("Last name cannot be empty");

        if (firstName != null && firstName.length() > 0)
            user.get().setFirstName(firstName);

        if (lastName != null && lastName.length() > 0)
            user.get().setLastName(lastName);
    }

    @Transactional
    public void updateName(String emailAddress, String firstName, String lastName) {
        if (emailAddress == null)
            throw new IllegalStateException("Student email not specified");

        Optional<User> user = userRepository.findByEmailAddress(emailAddress);

        // If first name is specified but is an empty string
        if (firstName != null && firstName.length() == 0)
            throw new IllegalStateException("First name cannot be empty");

        // If last name is specified but is an empty string
        if (lastName != null && lastName.length() == 0)
            throw new IllegalStateException("Last name cannot be empty");

        if (firstName != null && firstName.length() > 0)
            user.get().setFirstName(firstName);

        if (lastName != null && lastName.length() > 0)
            user.get().setLastName(lastName);
    }

    @Transactional
    public void updateGenderById(Integer id, Gender gender) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + id);
        userOptional.get().setGender(gender);
    }

    public void deleteUserById(Integer id) {
        boolean exists = userRepository.existsById(id);
        if (!exists)
            throw new UserNotFoundException("User not found by id " + id);

        userRepository.deleteById(id);
    }

    public void deleteUserByEmailAddress(String emailAddress) {
        boolean exists = userRepository.existsByEmailAddress(emailAddress);
        if (!exists)
            throw new UserNotFoundException("User not found by email address " + emailAddress);

        userRepository.deleteByEmailAddress(emailAddress);
    }
}
