package com.jvel.edify.controller;

import com.jvel.edify.controller.responses.CourseQueryMultipleResponse;
import com.jvel.edify.controller.responses.TeacherQueryMultipleResponse;
import com.jvel.edify.controller.responses.TeacherQueryResponse;
import com.jvel.edify.entity.Course;
import com.jvel.edify.entity.Teacher;
import com.jvel.edify.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/courses/id/{id}")
    public ResponseEntity<CourseQueryMultipleResponse> getCourses(@PathVariable("id") Integer teacherId) {
        List<Course> courses = teacherService.getCourses(teacherId);
        CourseQueryMultipleResponse cqmr = CourseQueryMultipleResponse.builder()
                .courses(courses)
                .build();
        return new ResponseEntity<>(
                cqmr,
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<TeacherQueryMultipleResponse> getTeachers() {
        TeacherQueryMultipleResponse teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(
                teachers,
                HttpStatus.OK
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TeacherQueryResponse> getTeacher(@PathVariable("id") Integer teacherId) {
        TeacherQueryResponse teacher = teacherService.getTeacherById(teacherId);
        return new ResponseEntity<>(
                teacher,
                HttpStatus.OK
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TeacherQueryResponse> getTeacher(@PathVariable("email") String emailAddress) {
        TeacherQueryResponse teacher = teacherService.getTeacherByEmailAddress(emailAddress);
        return new ResponseEntity<>(
                teacher,
                HttpStatus.OK
        );
    }
}
