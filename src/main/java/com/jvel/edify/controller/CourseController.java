package com.jvel.edify.controller;

import com.jvel.edify.config.JwtService;
import com.jvel.edify.controller.requests.CourseContentCreateRequest;
import com.jvel.edify.controller.requests.CourseCreateRequest;
import com.jvel.edify.controller.requests.CourseRequest;
import com.jvel.edify.controller.requests.CourseStudentRequest;
import com.jvel.edify.entity.Course;
import com.jvel.edify.entity.CourseContent;
import com.jvel.edify.entity.Student;
import com.jvel.edify.entity.Teacher;
import com.jvel.edify.service.CourseContentService;
import com.jvel.edify.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseContentService courseContentService;
    @Autowired
    private JwtService jwtService;
    @PostMapping()
    public ResponseEntity<String> addCourse(@RequestHeader("Authorization") String token, @RequestBody CourseCreateRequest course) {
        Integer id = jwtService.resolveToken(token);
        courseService.addCourse(course.getTitle(), course.getUnits(), id);
        return new ResponseEntity<>(
                "Course successfully added",
                HttpStatus.CREATED
        );
    }

    @GetMapping()
    public ResponseEntity<Course> getCourse(@RequestBody CourseRequest courseRequest) {
        Course course = courseService.getCourse(courseRequest.getCourseId());
        return new ResponseEntity<>(
                course,
                HttpStatus.OK
        );
    }

    @GetMapping("/content")
    public ResponseEntity<CourseContent> getCourseContent(@RequestBody CourseRequest courseRequest) {
        CourseContent courseContent = courseContentService.getCourseContent(courseRequest.getCourseId());
        return new ResponseEntity<>(
                courseContent,
                HttpStatus.OK
        );
    }

    @GetMapping("/teacher")
    public ResponseEntity<Teacher> getTeacher(@RequestBody CourseRequest courseRequest) {
        Teacher teacher = courseService.getTeacher(courseRequest.getCourseId());
        return new ResponseEntity<>(
                teacher,
                HttpStatus.OK
        );
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents(@RequestBody CourseRequest courseRequest) {
        List<Student> students = courseService.getStudents(courseRequest.getCourseId());
        return new ResponseEntity<>(
                students,
                HttpStatus.OK
        );
    }

    @PutMapping("/student")
    public ResponseEntity<String> addStudent(@RequestBody CourseStudentRequest csRequest) {
        courseService.addStudentToCourse(csRequest.getCourseId(), csRequest.getStudentId());
        return new ResponseEntity<>(
                "Student successfully added to course",
                HttpStatus.OK
        );
    }

    @PutMapping("/content")
    public ResponseEntity<String> addContent(@RequestHeader("Authorization") String token, @RequestBody CourseContentCreateRequest ccRequest) {
        Integer id = jwtService.resolveToken(token);
        courseContentService.addCourseContent(id, ccRequest.getContentURL(), ccRequest.getCourseId());
        return new ResponseEntity<>(
                "Content successfully added to course",
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/content")
    public ResponseEntity<String> deleteContent(@RequestHeader("Authorization") String token, @RequestBody CourseRequest course) {
        Integer id = jwtService.resolveToken(token);
        courseContentService.deleteCourseContent(id, course.getCourseId());
        return new ResponseEntity<>(
                "Content successfully deleted from course",
                HttpStatus.OK
        );
    }
}
