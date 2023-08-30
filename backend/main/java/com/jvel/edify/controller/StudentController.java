package com.jvel.edify.controller;

import com.jvel.edify.config.JwtService;
import com.jvel.edify.controller.requests.user_requests.student_requests.MajorRequest;
import com.jvel.edify.controller.responses.course_responses.CourseQueryMultipleResponse;
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

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentQueryResponse> getStudentByEmail(@PathVariable("email") String email) {
        StudentQueryResponse response = studentService.getStudentByEmailAddress(email);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/courses")
    public ResponseEntity<CourseQueryMultipleResponse> getCourses(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        CourseQueryMultipleResponse response = studentService.getCourses(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/courses/id/{id}")
    public ResponseEntity<CourseQueryMultipleResponse> getCoursesById(@PathVariable("id") Integer studentId) {
        CourseQueryMultipleResponse response = studentService.getCourses(studentId);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/courses/email/{email}")
    public ResponseEntity<CourseQueryMultipleResponse> getCoursesByEmail(@PathVariable("email") String emailAddress) {
        CourseQueryMultipleResponse response = studentService.getCourses(emailAddress);
        return new ResponseEntity<>(
                response,
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
