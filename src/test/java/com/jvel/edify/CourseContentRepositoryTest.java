package com.jvel.edify;

import com.jvel.edify.entity.Course;
import com.jvel.edify.entity.CourseContent;
import com.jvel.edify.repository.CourseContentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseContentRepositoryTest {
    @Autowired
    private CourseContentRepository courseContentRepository;

    @Test
    public void SaveCourseContent() {
        Course course = Course.builder()
                .title("DSA")
                .units(5)
                .build();
        CourseContent courseContent = CourseContent.builder()
                .url("www.test.com")
                .course(course)
                .build();

        courseContentRepository.save(courseContent);
    }

    @Test
    public void GetAllContent() {
        List<CourseContent> contents = courseContentRepository.findAll();
        System.out.println("contents = " + contents);
    }
}
