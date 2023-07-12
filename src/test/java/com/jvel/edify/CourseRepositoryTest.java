package com.jvel.edify;

import com.jvel.edify.course.Course;
import com.jvel.edify.course.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;
    
    @Test
    public void GetAllCourses() {
        List<Course> courses = courseRepository.findAll();
        System.out.println("courses = " + courses);
    }
}
