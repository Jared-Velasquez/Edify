package com.jvel.edify.service;

import com.jvel.edify.controller.requests.AuthenticationRequest;
import com.jvel.edify.controller.requests.RegisterRequest;
import com.jvel.edify.controller.responses.AuthenticationResponse;
import com.jvel.edify.config.JwtService;
import com.jvel.edify.controller.exceptions.DuplicateEntryException;
import com.jvel.edify.controller.exceptions.UserNotFoundException;
import com.jvel.edify.entity.Student;
import com.jvel.edify.entity.Teacher;
import com.jvel.edify.entity.User;
import com.jvel.edify.repository.StudentRepository;
import com.jvel.edify.repository.TeacherRepository;
import com.jvel.edify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (request.getEmailAddress() == null || request.getEmailAddress().length() == 0)
            throw new IllegalStateException("Email address not specified");
        if (request.getSsn() == null)
            throw new IllegalStateException("SSN not specified");

        boolean emailExists = userRepository.existsByEmailAddress(request.getEmailAddress());
        boolean ssnExists = userRepository.existsBySsn(request.getSsn());

        if (emailExists) throw new DuplicateEntryException("Email already exists");
        if (ssnExists) throw new DuplicateEntryException("SSN already exists");

        if (request.getRole().contains("STUDENT")) {
            return registerStudent(request);
        } else if (request.getRole().contains("TEACHER")) {
            return registerTeacher(request);
        } else {
            throw new IllegalArgumentException("Unmatched Role");
        }
    }
    public AuthenticationResponse registerStudent(RegisterRequest request) {
        if (request.getFirstName() == null || request.getFirstName().length() == 0)
            throw new IllegalStateException("First name not specified");
        if (request.getLastName() == null || request.getLastName().length() == 0)
            throw new IllegalStateException("Last name not specified");
        if (request.getDob() == null)
            throw new IllegalStateException("Date of Birth not specified");
        if (request.getPassword() == null || request.getPassword().length() == 0)
        if (request.getGender() == null || request.getGender().length() == 0)
            throw new IllegalStateException("Gender not specified");
        if (request.getAddress() == null || request.getAddress().length() == 0)
            throw new IllegalStateException("Address not specified");
        if (request.getPhoneNumber() == null || request.getPhoneNumber().length() == 0)
            throw new IllegalStateException("Phone number not specified");

        Student student = new Student(
                request.getFirstName(),
                request.getLastName(),
                request.getEmailAddress(),
                request.getSsn(),
                passwordEncoder.encode(request.getPassword()),
                request.getDob(),
                request.getGender(),
                request.getAddress(),
                request.getPhoneNumber()
        );

        Student savedStudent = studentRepository.saveAndFlush(student);
        Map<String, Object> customClaims = Map.of("id", savedStudent.getId());

        String jwtToken = jwtService.generateToken(customClaims, student);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerTeacher(RegisterRequest request) {
        if (request.getFirstName() == null || request.getFirstName().length() == 0)
            throw new IllegalStateException("First name not specified");
        if (request.getLastName() == null || request.getLastName().length() == 0)
            throw new IllegalStateException("Last name not specified");
        if (request.getDob() == null)
            throw new IllegalStateException("Date of Birth not specified");
        if (request.getPassword() == null || request.getPassword().length() == 0)
            throw new IllegalStateException("Password not specified");
        if (request.getGender() == null || request.getGender().length() == 0)
            throw new IllegalStateException("Gender not specified");
        if (request.getAddress() == null || request.getAddress().length() == 0)
            throw new IllegalStateException("Address not specified");
        if (request.getPhoneNumber() == null || request.getPhoneNumber().length() == 0)
            throw new IllegalStateException("Phone number not specified");

        Teacher teacher = new Teacher(
                request.getFirstName(),
                request.getLastName(),
                request.getEmailAddress(),
                request.getSsn(),
                passwordEncoder.encode(request.getPassword()),
                request.getDob(),
                request.getGender(),
                request.getAddress(),
                request.getPhoneNumber()
        );

        Teacher savedTeacher = teacherRepository.saveAndFlush(teacher);
        Map<String, Object> customClaims = Map.of("id", savedTeacher.getId());

        String jwtToken = jwtService.generateToken(customClaims, teacher);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (request.getEmailAddress() == null || request.getEmailAddress().length() == 0)
            throw new IllegalStateException("Email address not specified");
        if (request.getPassword() == null || request.getPassword().length() == 0)
            throw new IllegalStateException("Last name not specified");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailAddress(),
                        request.getPassword()
                )
        );
        Optional<User> user = userRepository.findByEmailAddress(request.getEmailAddress());
        if (user.isEmpty()) throw new UserNotFoundException("User not found by email address " + request.getEmailAddress());
        Map<String, Object> customClaims = Map.of("id", user.get().getId());
        String jwtToken = jwtService.generateToken(customClaims, user.get());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
