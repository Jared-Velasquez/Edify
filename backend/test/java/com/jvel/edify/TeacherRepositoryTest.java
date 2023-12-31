package com.jvel.edify;

import com.jvel.edify.entity.Course;
import com.jvel.edify.repository.CourseRepository;
import com.jvel.edify.entity.Teacher;
import com.jvel.edify.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void saveTeacher() {
        Teacher teacher = Teacher.builder()
                .firstName("Faul")
                .lastName("Eggert")
                .username("feggert")
                .build();

        Course course1 = Course.builder()
                .title("Software Construction")
                .units(4)
                .teacher(teacher)
                .build();
        Course course2 = Course.builder()
                .title("Operating Systems")
                .units(4)
                .teacher(teacher)
                .build();
        Course course3 = Course.builder()
                .title("Programming Languages")
                .units(4)
                .teacher(teacher)
                .build();

        teacherRepository.save(teacher);
        courseRepository.saveAll(List.of(course1, course2, course3));
    }
}
