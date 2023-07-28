package com.jvel.edify.controller;

import com.jvel.edify.controller.responses.StudentQueryMultipleResponse;
import com.jvel.edify.controller.responses.StudentQueryResponse;
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

    @PostMapping("/register")
    public ResponseEntity<String> addNewStudent(@RequestBody Student student) {
        try {
            studentService.addStudent(student);
            return new ResponseEntity<>(
                    "Student successfully added",
                    HttpStatus.CREATED
            );
        } catch(IllegalStateException ise) {
            return new ResponseEntity<>(
                    ise.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<StudentQueryMultipleResponse> getAllStudents() {
        return new ResponseEntity<>(
                studentService.getAllStudents(),
                HttpStatus.OK
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<StudentQueryResponse> getStudentById(@PathVariable("id") Integer id) {
        try {
            StudentQueryResponse response = studentService.getStudentById(id);
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentQueryResponse> getStudentByEmail(@PathVariable("email") String email) {
        try {
            StudentQueryResponse response = studentService.getStudentByEmailAddress(email);
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
