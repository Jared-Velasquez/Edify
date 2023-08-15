package com.jvel.edify.service;

import com.jvel.edify.controller.exceptions.*;
import com.jvel.edify.entity.*;
import com.jvel.edify.entity.enums.Role;
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
        if (teacher.isEmpty())
            throw new UserNotFoundException("User not found by id " + teacherId);
        if (teacher.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        // Check if course title is not taken
        boolean titleExists = courseRepository.existsByTitle(title);
        if (titleExists)
            throw new CourseAlreadyExistsException("Course already exists by title " + title);

        Course newCourse = Course.builder()
                .title(title)
                .units(units)
                .teacher((Teacher) teacher.get())
                .build();
        courseRepository.save(newCourse);
    }

    public void addCourse(String title, Integer units, String emailAddress) {
        // Check if teacher is present
        Optional<User> teacher = teacherRepository.findByEmailAddress(emailAddress);
        if (teacher.isEmpty())
            throw new UserNotFoundException("User not found by email address " + emailAddress);
        if (teacher.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by email address " + emailAddress);

        // Check if course title is not taken
        boolean titleExists = courseRepository.existsByTitle(title);
        if (titleExists)
            throw new CourseAlreadyExistsException("Course already exists by title " + title);

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
            throw new CourseNotFoundException("Course not found by id " + courseId);
        return courseOptional.get();
    }

    public Teacher getTeacher(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        return courseOptional.get().getTeacher();
    }

    public List<Student> getStudents(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        return courseOptional.get().getStudents();
    }

    @Transactional
    public void addStudentToCourse(Long courseId, Integer studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> studentOptional = studentRepository.findById(studentId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        if (studentOptional.isEmpty())
            throw new UserNotFoundException("Student not found by id " + studentId);
        if (studentOptional.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        Course course = courseOptional.get();
        Student student = (Student) studentOptional.get();

        // Check if student has already been added to course

        Student checkStudent = course.getStudents()
                .stream()
                .filter(stu -> studentId.equals(stu.getId()))
                .findAny()
                .orElse(null);
        if (checkStudent != null)
            throw new IllegalStateException("Student already added to course");

        course.addStudents(student);
    }
}
