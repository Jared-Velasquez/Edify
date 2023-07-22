package com.jvel.edify.auth;

import com.jvel.edify.auth.requests.AuthenticationRequest;
import com.jvel.edify.auth.requests.RegisterStudentRequest;
import com.jvel.edify.auth.responses.AuthenticationResponse;
import com.jvel.edify.config.JwtService;
import com.jvel.edify.entity.Student;
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
    public AuthenticationResponse registerStudent(RegisterStudentRequest request) {
        /*var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();*/
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

        var jwtToken = jwtService.generateToken(student);
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
        var user = userRepository.findByEmailAddress(request.getEmailAddress()).orElseThrow(); // ENSURE THIS STUDENT HAS A UNIQUE EMAIL ADDRESS
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
