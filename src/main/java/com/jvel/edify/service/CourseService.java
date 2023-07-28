package com.jvel.edify.service;

import com.jvel.edify.entity.*;
import com.jvel.edify.entity.roles.Role;
import com.jvel.edify.repository.CourseRepository;
import com.jvel.edify.repository.StudentRepository;
import com.jvel.edify.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    public void addCourse(String title, Integer units, Integer teacherId) {
        // Check if teacher is present
        Optional<User> teacher = teacherRepository.findById(teacherId);
        if (teacher.isEmpty() || (teacher.get().getRole() != Role.TEACHER))
            throw new IllegalArgumentException("no teacher found by id " + teacherId);

        // Check if course title is not taken
        boolean titleExists = courseRepository.existsByTitle(title);
        if (titleExists)
            throw new IllegalArgumentException("course title already exists");

        Course newCourse = Course.builder()
                .title(title)
                .units(units)
                .teacher((Teacher) teacher.get())
                .build();
        courseRepository.save(newCourse);
    }

    public Course getCourse(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new IllegalArgumentException("no course found by id " + courseId);
        return courseOptional.get();
    }

    public Teacher getTeacher(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new IllegalArgumentException("no course found by id " + courseId);
        return courseOptional.get().getTeacher();
    }

    public List<Student> getStudents(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new IllegalArgumentException("no course found by id " + courseId);
        return courseOptional.get().getStudents();
    }

    @Transactional
    public void addStudentToCourse(Long courseId, Integer studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> studentOptional = studentRepository.findById(studentId);
        if (courseOptional.isEmpty())
            throw new IllegalArgumentException("no course found by id " + courseId);
        if (studentOptional.isEmpty() || (studentOptional.get().getRole() != Role.STUDENT))
            throw new IllegalArgumentException("no student found by id " + studentId);

        Course course = courseOptional.get();
        Student student = (Student) studentOptional.get();
        course.addStudents(student);
    }
}
