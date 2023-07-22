package com.jvel.edify;

import com.jvel.edify.entity.Student;
import com.jvel.edify.entity.User;
import com.jvel.edify.repository.StudentRepository;
import com.jvel.edify.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class StudentRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudent() {
        Student student = Student.builder()
                .emailAddress("jaredvel25@gmail.com")
                .firstName("Jared")
                .lastName("Velasquez")
                .gender("Male")
                .dob(Date.valueOf("2003-07-10"))
                .address("1600 Pennsylvania Avenue")
                .phoneNumber("123-456-7890")
                .ssn(1234567890)
                .password("test123")
                .build();

        studentRepository.save(student);
    }

    @Test
    public void findStudentName() {
        // This will get the list of users whose name is "Jared Velasquez"; this query will return the child objects
        List<User> selectedStudent = studentRepository.findByFirstNameAndLastName("Jared", "Velasquez");
        System.out.println("selectedStudent = " + selectedStudent);
    }

    @Test
    public void getAllStudents() {
        List<User> allStudents = studentRepository.findAll();
        System.out.println("allStudents = " + allStudents);
    }

    @Test
    public void findEmail() {
        Optional<User> student = studentRepository.findByEmailAddress("jaredvel24@gmail.com");
        if (student.isPresent()) {
            System.out.println("student = " + student.get());
        } else {
            System.out.println("No student available");
        }
    }

    @Test
    public void updateFirstNameTest() {
        studentRepository.updateFirstNameByEmailAddress("Jared 3", "jaredvel25@gmail.com");
    }
}
