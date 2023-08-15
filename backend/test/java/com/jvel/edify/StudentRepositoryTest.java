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

}
