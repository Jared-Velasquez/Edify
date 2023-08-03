package com.jvel.edify.service;

import com.jvel.edify.controller.exceptions.StudentNotFoundException;
import com.jvel.edify.controller.exceptions.TeacherNotFoundException;
import com.jvel.edify.controller.exceptions.UserNotFoundException;
import com.jvel.edify.controller.responses.CourseQueryMultipleResponse;
import com.jvel.edify.controller.responses.TeacherQueryMultipleResponse;
import com.jvel.edify.controller.responses.TeacherQueryResponse;
import com.jvel.edify.entity.Course;
import com.jvel.edify.entity.Teacher;
import com.jvel.edify.entity.User;
import com.jvel.edify.entity.roles.Role;
import com.jvel.edify.repository.CourseRepository;
import com.jvel.edify.repository.TeacherRepository;
import com.jvel.edify.util.StreamFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;

    public TeacherQueryMultipleResponse getAllTeachers() {
        List<User> teachers = teacherRepository.findAll().stream().filter(StreamFilters.byTeacher).collect(Collectors.toList());
        return TeacherQueryMultipleResponse.builder()
                .teachers(teachers)
                .build();
    }

    public TeacherQueryResponse getTeacherById(Integer teacherId) {
        Optional<User> teacher = teacherRepository.findById(teacherId);

        if (teacher.isEmpty())
            throw new UserNotFoundException("User not found by id " + teacherId);
        if (teacher.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        return TeacherQueryResponse.builder()
                .teacher(teacher.get())
                .build();
    }

    public TeacherQueryResponse getTeacherByEmailAddress(String emailAddress) {
        Optional<User> teacher = teacherRepository.findByEmailAddress(emailAddress);

        if (teacher.isEmpty())
            throw new UserNotFoundException("User not found by email address " + emailAddress);
        if (teacher.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by email address " + emailAddress);

        return TeacherQueryResponse.builder()
                .teacher(teacher.get())
                .build();
    }

    public CourseQueryMultipleResponse getCourses(Integer teacherId) {
        Optional<User> teacherOptional = teacherRepository.findById(teacherId);

        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + teacherId);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        List<Course> coursesList = courseRepository.findByTeacher((Teacher) teacherOptional.get());
        CourseQueryMultipleResponse courses = CourseQueryMultipleResponse.builder()
                .courses(coursesList)
                .build();
        return courses;
    }

    public CourseQueryMultipleResponse getCoursesByEmailAddress(String emailAddress) {
        Optional<User> teacherOptional = teacherRepository.findByEmailAddress(emailAddress);

        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("No user found by email address " + emailAddress);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("No teacher found by email address " + emailAddress);

        List<Course> coursesList = courseRepository.findByTeacher((Teacher) teacherOptional.get());
        CourseQueryMultipleResponse courses = CourseQueryMultipleResponse.builder()
                .courses(coursesList)
                .build();
        return courses;
    }
}
