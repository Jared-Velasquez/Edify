package com.jvel.edify.service;

import com.jvel.edify.controller.responses.StudentQueryMultipleResponse;
import com.jvel.edify.controller.responses.StudentQueryResponse;
import com.jvel.edify.entity.Student;
import com.jvel.edify.entity.User;
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
                .build();
    }

    public StudentQueryResponse getStudentById(Integer studentId) {
        Optional<User> student = studentRepository.findById(studentId);

        /*if (student.isPresent())
            return StudentQueryResponse.builder()
                    .student(student.get())
                    .build();*/
        return StudentQueryResponse.builder()
                .student(null)
                .build();
    }

    public StudentQueryResponse getStudentByEmailAddress(String emailAddress) {
        //Optional<Student> student = studentRepository.findByEmailAddress(emailAddress);

        /*if (student.isPresent())
            return StudentQueryResponse.builder()
                    .student(student.get())
                    .build();*/
        return StudentQueryResponse.builder()
                .student(null)
                .build();
    }

    @Transactional
    public void updateStudentEmail(Integer studentId, String newEmail) {
        if (studentId == null)
            throw new IllegalStateException("Student ID not specified");
        if (newEmail == null || newEmail.length() == 0)
            throw new IllegalStateException("Desired email not specified");

        Optional<User> studentWithId = studentRepository.findById(studentId);
        Optional<User> studentNewEmail = studentRepository.findByEmailAddress(newEmail);

        // If no student has this old email
        if (!studentWithId.isPresent())
            throw new IllegalStateException("Student with email " + studentId + " does not exist");
        // If a student already has this new email
        if (studentNewEmail.isPresent())
            throw new IllegalStateException("Student with email " + newEmail + " already exists");

        studentWithId.get().setEmailAddress(newEmail);
    }

    @Transactional
    public void updateStudentEmail(String oldEmail, String newEmail) {
        if (oldEmail == null || oldEmail.length() == 0)
            throw new IllegalStateException("Current email not specified");
        if (newEmail == null || newEmail.length() == 0)
            throw new IllegalStateException("Desired email not specified");

        //Optional<Student> studentOldEmail = studentRepository.findByEmailAddress(oldEmail);
        //Optional<Student> studentNewEmail = studentRepository.findByEmailAddress(newEmail);

        // If no student has this old email
        /*if (!studentOldEmail.isPresent())
            throw new IllegalStateException("Student with email " + oldEmail + " does not exist");
        // If a student already has this new email
        if (studentNewEmail.isPresent())
            throw new IllegalStateException("Student with email " + newEmail + " already exists");

        studentOldEmail.get().setEmailAddress(newEmail);*/
    }

    @Transactional
    public void updateStudentName(Integer studentId, String firstName, String lastName) {
        if (studentId == null)
            throw new IllegalStateException("Student ID not specified");

        Optional<User> user = studentRepository.findById(studentId);

        // If first name is specified but is an empty string
        if (firstName != null && firstName.length() == 0)
            throw new IllegalStateException("First name cannot be empty");

        // If last name is specified but is an empty string
        if (lastName != null && lastName.length() == 0)
            throw new IllegalStateException("Last name cannot be empty");

        /*if (firstName != null && firstName.length() > 0)
            student.get().setFirstName(firstName);

        if (lastName != null && lastName.length() > 0)
            student.get().setLastName(lastName);*/
    }

    @Transactional
    public void updateStudentName(String emailAddress, String firstName, String lastName) {
        if (emailAddress == null)
            throw new IllegalStateException("Student email not specified");

        //Optional<Student> student = studentRepository.findByEmailAddress(emailAddress);

        // If first name is specified but is an empty string
        if (firstName != null && firstName.length() == 0)
            throw new IllegalStateException("First name cannot be empty");

        // If last name is specified but is an empty string
        if (lastName != null && lastName.length() == 0)
            throw new IllegalStateException("Last name cannot be empty");

        /*if (firstName != null && firstName.length() > 0)
            student.get().setFirstName(firstName);

        if (lastName != null && lastName.length() > 0)
            student.get().setLastName(lastName);*/
    }


    public void deleteStudent(Long studentId) {
        //boolean exists = studentRepository.existsById(studentId);
        boolean exists = false;
        if (!exists)
            throw new IllegalStateException("Student with id " + studentId + " does not exist");

        //studentRepository.deleteById(studentId);
    }

    public void deleteStudent(String emailAddress) {
        boolean exists = studentRepository.existsByEmailAddress(emailAddress);
        if (!exists)
            throw new IllegalStateException("Student with email " + emailAddress + " does not exist");

        //studentRepository.deleteByEmailAddress(emailAddress);
    }

}
