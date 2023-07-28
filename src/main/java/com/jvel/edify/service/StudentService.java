package com.jvel.edify.service;

import com.jvel.edify.controller.responses.StudentQueryMultipleResponse;
import com.jvel.edify.controller.responses.StudentQueryResponse;
import com.jvel.edify.entity.Student;
import com.jvel.edify.entity.User;
import com.jvel.edify.entity.roles.Role;
import com.jvel.edify.repository.CourseRepository;
import com.jvel.edify.repository.StudentRepository;
import com.jvel.edify.repository.TeacherRepository;
import com.jvel.edify.repository.UserRepository;
import com.jvel.edify.util.StreamFilters;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;

    public void addStudent(Student student) {
        //System.out.println("student = " + student);
        // Check if email is present
        if (student.getEmailAddress() == null)
            throw new IllegalStateException("Email address is not present");

        // Check if SSN is present
        if (student.getSsn() == null)
            throw new IllegalStateException("SSN is not present");

        // Check if another user has the same email address as this student
        Optional<User> userOptionalEmail = userRepository.findByEmailAddress(student.getEmailAddress());
        if (userOptionalEmail.isPresent())
            throw new IllegalStateException("email taken");

        // Check if another student has the sane SSN as this student
        Optional<User> userOptionalSSN = userRepository.findBySsn(student.getSsn());
        if (userOptionalSSN.isPresent())
            throw new IllegalStateException("ssn taken");

        studentRepository.save(student);
    }

    public StudentQueryMultipleResponse getAllStudents() {
        List<User> students = studentRepository.findAll().stream().filter(StreamFilters.byStudent).collect(Collectors.toList());
        return StudentQueryMultipleResponse.builder()
                .students(students)
                .build();
    }

    public StudentQueryResponse getStudentById(Integer studentId) {
        Optional<User> student = studentRepository.findById(studentId);

        if (student.isPresent() && student.get().getRole() == Role.STUDENT)
            return StudentQueryResponse.builder()
                    .student(student.get())
                    .build();
        return StudentQueryResponse.builder()
                .student(null)
                .build();
    }

    public StudentQueryResponse getStudentByEmailAddress(String emailAddress) {
        Optional<User> student = studentRepository.findByEmailAddress(emailAddress);

        if (student.isPresent() && student.get().getRole() == Role.STUDENT)
            return StudentQueryResponse.builder()
                    .student(student.get())
                    .build();
        return StudentQueryResponse.builder()
                .student(null)
                .build();
    }
}
