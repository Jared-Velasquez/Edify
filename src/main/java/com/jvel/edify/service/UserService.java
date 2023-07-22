package com.jvel.edify.service;

import com.jvel.edify.entity.User;
import com.jvel.edify.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updateEmail(Integer id, String newEmail) {
        if (id == null)
            throw new IllegalStateException("ID not specified");
        if (newEmail == null || newEmail.length() == 0)
            throw new IllegalStateException("Desired email not specified");

        Optional<User> userWithId = userRepository.findById(id);
        Optional<User> userNewEmail = userRepository.findByEmailAddress(newEmail);

        // If no student has this old email
        if (!userWithId.isPresent())
            throw new IllegalStateException("Student with email " + id + " does not exist");
        // If a student already has this new email
        if (userNewEmail.isPresent())
            throw new IllegalStateException("Student with email " + newEmail + " already exists");

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
        if (!userOldEmail.isPresent())
            throw new IllegalStateException("Student with email " + oldEmail + " does not exist");
        // If a student already has this new email
        if (userNewEmail.isPresent())
            throw new IllegalStateException("Student with email " + newEmail + " already exists");

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

    public void deleteUser(Integer id) {
        boolean exists = userRepository.existsById(id);
        if (!exists)
            throw new IllegalStateException("User with id " + id + " does not exist");

        userRepository.deleteById(id);
    }

    public void deleteStudent(String emailAddress) {
        boolean exists = userRepository.existsByEmailAddress(emailAddress);
        if (!exists)
            throw new IllegalStateException("User with email " + emailAddress + " does not exist");

        userRepository.deleteByEmailAddress(emailAddress);
    }
}
