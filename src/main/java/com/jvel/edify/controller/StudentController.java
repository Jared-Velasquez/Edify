package com.jvel.edify.controller;

import com.jvel.edify.controller.responses.StudentQueryResponse;
import com.jvel.edify.entity.Student;
import com.jvel.edify.repository.StudentRepository;
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

    @GetMapping(path = "/all")
    public ResponseEntity<StudentQueryResponse> getAllStudents() {
        return new ResponseEntity<>(
                studentService.getAllStudents(),
                HttpStatus.OK
        );
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewStudent(@RequestBody Student student) {
        try {
            studentService.addStudent(student);
            return new ResponseEntity<>(
                    "Student successfully added",
                    HttpStatus.OK
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

    @DeleteMapping
    public ResponseEntity<String> deleteStudentById(@RequestParam Long id) {
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(
                    "Student successfully deleted",
                    HttpStatus.OK
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

    @DeleteMapping
    public ResponseEntity<String> deleteStudentByEmail(@RequestParam String email) {
        try {
            studentService.deleteStudent(email);
            return new ResponseEntity<>(
                    "Student successfully deleted",
                    HttpStatus.OK
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
}
