package com.jvel.edify.controller;

import com.jvel.edify.config.JwtService;
import com.jvel.edify.controller.requests.user_requests.teacher_requests.DepartmentRequest;
import com.jvel.edify.controller.requests.user_requests.teacher_requests.PositionRequest;
import com.jvel.edify.controller.responses.course_responses.CourseQueryMultipleResponse;
import com.jvel.edify.controller.responses.user_responses.teacher_responses.TeacherQueryMultipleResponse;
import com.jvel.edify.controller.responses.user_responses.teacher_responses.TeacherQueryResponse;
import com.jvel.edify.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<TeacherQueryResponse> getTeacher(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        TeacherQueryResponse response = teacherService.getTeacherById(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/courses")
    public ResponseEntity<CourseQueryMultipleResponse> getCourses(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        CourseQueryMultipleResponse cqmr = teacherService.getCourses(id);
        return new ResponseEntity<>(
                cqmr,
                HttpStatus.OK
        );
    }

    @GetMapping("/courses/id/{id}")
    public ResponseEntity<CourseQueryMultipleResponse> getCoursesById(@PathVariable("id") Integer teacherId) {
        CourseQueryMultipleResponse courses = teacherService.getCourses(teacherId);
        return new ResponseEntity<>(
                courses,
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
    public ResponseEntity<TeacherQueryResponse> getTeacherById(@PathVariable("id") Integer teacherId) {
        TeacherQueryResponse teacher = teacherService.getTeacherById(teacherId);
        return new ResponseEntity<>(
                teacher,
                HttpStatus.OK
        );
    }

    @PutMapping("/department")
    public ResponseEntity<CourseQueryMultipleResponse> updateDepartment(
            @RequestHeader("Authorization") String token,
            @RequestBody DepartmentRequest departmentRequest) {
        Integer id = jwtService.resolveToken(token);
        System.out.println("Request: " + departmentRequest);
        teacherService.updateDepartmentById(id, departmentRequest.getDepartment());
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PutMapping("/position")
    public ResponseEntity<CourseQueryMultipleResponse> updatePosition(
            @RequestHeader("Authorization") String token,
            @RequestBody PositionRequest positionRequest) {
        Integer id = jwtService.resolveToken(token);
        System.out.println(positionRequest);
        teacherService.updatePositionById(id, positionRequest.getPosition());
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}
