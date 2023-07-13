package com.jvel.edify.service;

import com.jvel.edify.controller.responses.StudentQueryResponse;
import com.jvel.edify.entity.Student;
import com.jvel.edify.repository.CourseRepository;
import com.jvel.edify.repository.StudentRepository;
import com.jvel.edify.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;

    public StudentQueryResponse getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return StudentQueryResponse.builder()
                .students(students)
                .build();
    }

    public void addStudent(Student student) {
        // Check if email is present
        if (student.getEmailAddress() == null)
            throw new IllegalStateException("Email address is not present");

        // Check if SSN is present
        if (student.getSSN() == null)
            throw new IllegalStateException("SSN is not present");

        // Check if another student has the same email address as this student
        Optional<Student> studentOptional = studentRepository.findByEmailAddress(student.getEmailAddress());
        if (studentOptional.isPresent())
            throw new IllegalStateException("email taken");

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    public void deleteStudent(String emailAddress) {
        boolean exists = studentRepository.existsByEmailAddress(emailAddress);
        if (!exists) {
            throw new IllegalStateException("student with email " + emailAddress + " does not exist");
        }
        studentRepository.deleteByEmailAddress(emailAddress);
    }

}
