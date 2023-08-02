package com.jvel.edify.controller;

import com.jvel.edify.controller.responses.CourseQueryMultipleResponse;
import com.jvel.edify.entity.Course;
import com.jvel.edify.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/courses")
    public ResponseEntity<CourseQueryMultipleResponse> getCourses(@RequestBody Integer teacherId) {
        List<Course> courses = teacherService.getCourses(teacherId);
        CourseQueryMultipleResponse cqmr = CourseQueryMultipleResponse.builder()
                .courses(courses)
                .build();
        return new ResponseEntity<>(
                cqmr,
                HttpStatus.OK
        );
    }

    @GetMapping()
    public ResponseEntity<String> test() {
        return new ResponseEntity<>(
                "Test",
                HttpStatus.OK
        );
    }
}
