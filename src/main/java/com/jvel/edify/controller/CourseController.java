package com.jvel.edify.controller;

import com.jvel.edify.controller.requests.CourseContentCreateRequest;
import com.jvel.edify.controller.requests.CourseCreateRequest;
import com.jvel.edify.controller.requests.CourseRequest;
import com.jvel.edify.controller.requests.CourseStudentRequest;
import com.jvel.edify.entity.CourseContent;
import com.jvel.edify.service.CourseContentService;
import com.jvel.edify.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseContentService courseContentService;
    @PostMapping("/create")
    public ResponseEntity<String> addCourse(@RequestBody CourseCreateRequest course) {
        try {
            courseService.addCourse(course.getTitle(), course.getUnits(), course.getTeacherId());
            return new ResponseEntity<>(
                    "Course successfully added",
                    HttpStatus.CREATED
            );
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(
                    iae.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("/add-student")
    public ResponseEntity<String> addStudent(@RequestBody CourseStudentRequest csRequest) {
        try {
            courseService.addStudentToCourse(csRequest.getCourseId(), csRequest.getStudentId());
            return new ResponseEntity<>(
                    "Student successfully added to course",
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("/content")
    public ResponseEntity<String> addContent(@RequestBody CourseContentCreateRequest ccRequest) {
        try {
            courseContentService.addCourseContent(ccRequest.getContentURL(), ccRequest.getCourseId());
            return new ResponseEntity<>(
                    "Content successfully added to course",
                    HttpStatus.CREATED
            );
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(
                    iae.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping("/content")
    public ResponseEntity<String> deleteContent(@RequestBody CourseRequest course) {
        try {
            courseContentService.deleteCourseContent(course.getCourseId());
            return new ResponseEntity<>(
                    "Content successfully deleted from course",
                    HttpStatus.OK
            );
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(
                    iae.getMessage(),
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
