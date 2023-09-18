package com.jvel.edify.controller;

import com.jvel.edify.config.JwtService;
import com.jvel.edify.controller.requests.user_requests.student_requests.MajorRequest;
import com.jvel.edify.controller.requests.user_requests.student_requests.ScoreRequest;
import com.jvel.edify.controller.responses.course_responses.*;
import com.jvel.edify.controller.responses.user_responses.student_responses.ScoreQueryMultipleResponse;
import com.jvel.edify.controller.responses.user_responses.student_responses.StudentQueryMultipleResponse;
import com.jvel.edify.controller.responses.user_responses.student_responses.StudentQueryResponse;
import com.jvel.edify.entity.Student;
import com.jvel.edify.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> addNewStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return new ResponseEntity<>(
                "Student successfully added",
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/all")
    public ResponseEntity<StudentQueryMultipleResponse> getAllStudents() {
        return new ResponseEntity<>(
                studentService.getAllStudents(),
                HttpStatus.OK
        );
    }
    
    @GetMapping()
    public ResponseEntity<StudentQueryResponse> getStudent(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        StudentQueryResponse response = studentService.getStudentById(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<StudentQueryResponse> getStudentById(@PathVariable("id") Integer id) {
        StudentQueryResponse response = studentService.getStudentById(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/courses")
    public ResponseEntity<CourseTeacherMultipleResponse> getCourses(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        CourseTeacherMultipleResponse response = studentService.getCourses(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/scores")
    public ResponseEntity<ScoreQueryMultipleResponse> getScores(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        return new ResponseEntity<>(
                studentService.getScores(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/assignments")
    public ResponseEntity<AssignmentCourseQueryMultipleResponse> getAssignments(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        return new ResponseEntity<>(
                studentService.getAssignments(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<CourseQueryMultipleResponse> getUnenrolledCourses(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        return new ResponseEntity<>(
                studentService.getUnenrolledCourses(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/scores")
    public ResponseEntity<String> updateScore(@RequestHeader("Authorization") String token, @RequestBody ScoreRequest sr) {
        Integer id = jwtService.resolveToken(token);
        studentService.updateScore(id, sr.getScore(), sr.getAssignmentId());
        return new ResponseEntity<>(
                "Score successfully updated",
                HttpStatus.OK
        );
    }

    @PutMapping("/major")
    public ResponseEntity<CourseQueryMultipleResponse> updateMajor(
            @RequestHeader("Authorization") String token,
            @RequestBody MajorRequest majorRequest) {
        Integer id = jwtService.resolveToken(token);
        studentService.updateMajorById(id, majorRequest.getMajor());
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}
