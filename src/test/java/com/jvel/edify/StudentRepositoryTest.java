package com.jvel.edify;

import com.jvel.edify.student.Student;
import com.jvel.edify.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudent() {
        Student student = Student.builder()
                .emailAddress("jaredvel25@gmail.com")
                .firstName("Jared")
                .lastName("Velasquez")
                .gender("Male")
                .DOB(Date.valueOf("2003-07-10"))
                .address("1600 Pennsylvania Avenue")
                .phoneNumber("123-456-7890")
                .SSN(1234567890)
                .build();

        studentRepository.save(student);
    }

    @Test
    public void findStudentName() {
        List<Student> selectedStudent = studentRepository.findByFirstNameAndLastName("Jared", "Velasquez");
        System.out.println("selectedStudent = " + selectedStudent);
    }

    @Test
    public void getAllStudents() {
        List<Student> allStudents = studentRepository.findAll();
        System.out.println("allStudents = " + allStudents);
    }

    @Test
    public void findEmail() {
        Optional<Student> student = studentRepository.findByEmailAddress("jaredvel25@gmail.com");
        if (student.isPresent()) {
            System.out.println("student = " + student.get());
        } else {
            System.out.println("No student available");
        }
    }

    @Test
    public void updateFirstNameTest() {
        studentRepository.updateFirstNameByEmailAddress("Darren", "jaredvel24@gmail.com");
    }
}
