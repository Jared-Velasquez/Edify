package com.jvel.edify.service;

import com.jvel.edify.controller.exceptions.StudentNotFoundException;
import com.jvel.edify.controller.exceptions.UserAlreadyExistsException;
import com.jvel.edify.controller.exceptions.UserNotFoundException;
import com.jvel.edify.controller.responses.CourseQueryMultipleResponse;
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
            throw new UserAlreadyExistsException("Email already taken by user");

        // Check if another student has the same SSN as this student
        Optional<User> userOptionalSSN = userRepository.findBySsn(student.getSsn());
        if (userOptionalSSN.isPresent())
            throw new UserAlreadyExistsException("SSN already taken by user");

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

        if (student.isEmpty())
            throw new UserNotFoundException("User not found by id " + studentId);
        if (student.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        return StudentQueryResponse.builder()
                .student(student.get())
                .build();
    }

    public StudentQueryResponse getStudentByEmailAddress(String emailAddress) {
        Optional<User> student = studentRepository.findByEmailAddress(emailAddress);

        if (student.isEmpty())
            throw new UserNotFoundException("User not found by email address " + emailAddress);
        if (student.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by email address " + emailAddress);

        return StudentQueryResponse.builder()
                .student(student.get())
                .build();
    }

    public CourseQueryMultipleResponse getCourses(Integer studentId) {
        Optional<User> user = studentRepository.findById(studentId);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found by id " + studentId);
        if (user.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        Student student = (Student) user.get();

        return CourseQueryMultipleResponse.builder()
                .courses(student.getCourses())
                .build();
    }

    public CourseQueryMultipleResponse getCourses(String emailAddress) {
        Optional<User> user = studentRepository.findByEmailAddress(emailAddress);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found by id " + emailAddress);
        if (user.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + emailAddress);

        Student student = (Student) user.get();

        return CourseQueryMultipleResponse.builder()
                .courses(student.getCourses())
                .build();
    }
}
