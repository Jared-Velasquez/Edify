package com.jvel.edify;

import com.jvel.edify.entity.Course;
import com.jvel.edify.entity.User;
import com.jvel.edify.repository.CourseRepository;
import com.jvel.edify.entity.Student;
import com.jvel.edify.repository.StudentRepository;
import com.jvel.edify.entity.Teacher;
import com.jvel.edify.repository.TeacherRepository;
import com.jvel.edify.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CourseRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Test
    public void GetAllCourses() {
        List<Course> courses = courseRepository.findAll();
        System.out.println("courses = " + courses);
    }

    @Test
    public void saveCourseWithStudentAndTeacher() {
        Teacher teacher = Teacher.builder()
                .firstName("Glenn")
                .lastName("Reinman")
                .username("reinman4")
                .build();
        Course course = Course.builder()
                .title("Introduction to Computer Organization")
                .units(4)
                .teacher(teacher)
                .build();
        Optional<User> student = userRepository.findByUsername("jaredvel");
        //course.addStudents(student.get());

        teacherRepository.save(teacher);
        courseRepository.save(course);
    }
}
