package com.jvel.edify.auth;

import com.jvel.edify.auth.requests.AuthenticationRequest;
import com.jvel.edify.auth.requests.RegisterRequest;
import com.jvel.edify.auth.responses.AuthenticationResponse;
import com.jvel.edify.config.JwtService;
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
        boolean emailExists = userRepository.existsByEmailAddress(request.getEmailAddress());
        boolean ssnExists = userRepository.existsBySsn(request.getSsn());

        if (emailExists) throw new IllegalArgumentException("Email already exists");
        if (ssnExists) throw new IllegalArgumentException("SSN already exists");

        if (request.getRole().contains("STUDENT")) {
            return registerStudent(request);
        } else if (request.getRole().contains("TEACHER")) {
            return registerTeacher(request);
        } else {
            throw new IllegalArgumentException("Unmatched Role");
        }
    }
    public AuthenticationResponse registerStudent(RegisterRequest request) {
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

        studentRepository.save(student);

        String jwtToken = jwtService.generateToken(student);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerTeacher(RegisterRequest request) {
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

        teacherRepository.save(teacher);

        String jwtToken = jwtService.generateToken(teacher);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailAddress(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmailAddress(request.getEmailAddress()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
