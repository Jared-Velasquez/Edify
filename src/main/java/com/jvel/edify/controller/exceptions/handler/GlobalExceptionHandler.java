package com.jvel.edify.controller.exceptions.handler;

import com.jvel.edify.controller.exceptions.*;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> userNotFound(UserNotFoundException unfe) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "UserNotFoundException");
        map.put("error", unfe.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> userAlreadyExists(UserAlreadyExistsException uaee) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "UserAlreadyExistsException");
        map.put("error", uaee.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = StudentNotFoundException.class)
    public ResponseEntity<Map<String, String>> studentNotFound(StudentNotFoundException snfe) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "StudentNotFoundException");
        map.put("error", snfe.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = StudentAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> studentAlreadyExists(StudentAlreadyExistsException saee) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "UserAlreadyExistsException");
        map.put("error", saee.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = TeacherNotFoundException.class)
    public ResponseEntity<Map<String, String>> teacherNotFound(TeacherNotFoundException tnfe) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "TeacherNotFoundException");
        map.put("error", tnfe.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = TeacherAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> teacherAlreadyExists(TeacherAlreadyExistsException taee) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "TeacherAlreadyExistsException");
        map.put("error", taee.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = CourseNotFoundException.class)
    public ResponseEntity<Map<String, String>> courseNotFound(CourseNotFoundException cnfe) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "CourseNotFoundException");
        map.put("error", cnfe.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = CourseAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> courseAlreadyExists(CourseAlreadyExistsException caee) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "CourseAlreadyExistsException");
        map.put("error", caee.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = ContentNotFoundException.class)
    public ResponseEntity<Map<String, String>> contentNotFound(ContentNotFoundException cnfe) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "ContentNotFoundException");
        map.put("error", cnfe.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = ContentAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> contentAlreadyExists(ContentAlreadyExistsException caee) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "ContentAlreadyExistsException");
        map.put("error", caee.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = DuplicateEntryException.class)
    public ResponseEntity<Map<String, String>> duplicateEntry(DuplicateEntryException dee) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "DuplicateEntryException");
        map.put("error", dee.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> illegalArgument(IllegalArgumentException iae) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "IllegalArgumentException");
        map.put("error", iae.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<Map<String, String>> illegalState(IllegalStateException ise) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "IllegalStateException");
        map.put("error", ise.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = HibernateException.class)
    public ResponseEntity<Map<String, String>> hibernate(HibernateException he) {
        HashMap<String, String> map = new HashMap<>();
        map.put("exception", "HibernateException");
        map.put("error", he.getMessage());
        return new ResponseEntity<>(
                map,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
