package com.jvel.edify.controller;

import com.jvel.edify.config.JwtService;
import com.jvel.edify.controller.requests.course_requests.*;
import com.jvel.edify.controller.responses.course_responses.AnnouncementQueryMultipleResponse;
import com.jvel.edify.controller.responses.course_responses.AssignmentQueryMultipleResponse;
import com.jvel.edify.controller.responses.course_responses.AssignmentQueryResponse;
import com.jvel.edify.controller.responses.course_responses.ModuleQueryMultipleResponse;
import com.jvel.edify.entity.*;
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

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@RequestHeader("Authorization") String token, @PathVariable Long courseId) {
        Integer id = jwtService.resolveToken(token);
        Course course = courseService.getCourse(id, courseId);
        return new ResponseEntity<>(
                course,
                HttpStatus.OK
        );
    }

    @PutMapping()
    public ResponseEntity<String> updateCourse(@RequestHeader("Authorization") String token, @RequestBody UpdateCourse course) {
        Integer id = jwtService.resolveToken(token);
        courseService.updateCourse(id, course);
        return new ResponseEntity<>(
                "Course successfully updated",
                HttpStatus.OK
        );
    }

    @GetMapping("/teacher/{courseId}")
    public ResponseEntity<Teacher> getTeacher(@RequestHeader("Authorization") String token, @PathVariable Long courseId) {
        Integer id = jwtService.resolveToken(token);
        Teacher teacher = courseService.getTeacher(id, courseId);
        return new ResponseEntity<>(
                teacher,
                HttpStatus.OK
        );
    }

    @GetMapping("/students/{courseId}")
    public ResponseEntity<List<Student>> getStudents(@RequestHeader("Authorization") String token, @PathVariable Long courseId) {
        Integer id = jwtService.resolveToken(token);
        List<Student> students = courseService.getStudents(id, courseId);
        return new ResponseEntity<>(
                students,
                HttpStatus.OK
        );
    }

    @PutMapping("/student")
    public ResponseEntity<String> addStudent(@RequestHeader("Authorization") String token, @RequestBody CourseRequest courseRequest) {
        Integer id = jwtService.resolveToken(token);
        courseService.addStudentToCourse(courseRequest.getCourseId(), id);
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

    @PostMapping("/module")
    public ResponseEntity<String> addModule(@RequestHeader("Authorization") String token, @RequestBody ModuleCreateRequest module) {
        Integer id = jwtService.resolveToken(token);
        courseService.addModuleToCourse(id, module);
        return new ResponseEntity<>(
                "Module successfully added to course",
                HttpStatus.CREATED
        );
    }

    @PostMapping("/assignment")
    public ResponseEntity<String> addAssignment(@RequestHeader("Authorization") String token, @RequestBody AssignmentCreateRequest assignment) {
        Integer id = jwtService.resolveToken(token);
        courseService.addAssignmentToModule(id, assignment);
        return new ResponseEntity<>(
                "Assignment successfully added to course",
                HttpStatus.CREATED
        );
    }

    @PostMapping("/announcement")
    public ResponseEntity<String> addAnnouncement(@RequestHeader("Authorization") String token, @RequestBody AnnouncementCreateRequest announcement) {
        Integer id = jwtService.resolveToken(token);
        courseService.addAnnouncementToCourse(id, announcement);
        return new ResponseEntity<>(
                "Announcement successfully added to course",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/module/{courseId}")
    public ResponseEntity<ModuleQueryMultipleResponse> getModules(@RequestHeader("Authorization") String token, @PathVariable Long courseId) {
        Integer id = jwtService.resolveToken(token);
        return new ResponseEntity<>(
                courseService.getModules(id, courseId),
                HttpStatus.OK
        );
    }

    @GetMapping("/assignments/{courseId}")
    public ResponseEntity<AssignmentQueryMultipleResponse> getAssignments(@RequestHeader("Authorization") String token, @PathVariable Long courseId) {
        Integer id = jwtService.resolveToken(token);
        return new ResponseEntity<>(
                courseService.getAssignments(id, courseId),
                HttpStatus.OK
        );
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<AssignmentQueryResponse> getAssignment(@RequestHeader("Authorization") String token, @PathVariable Integer assignmentId) {
        Integer id = jwtService.resolveToken(token);
        return new ResponseEntity<>(
                courseService.getAssignment(id, assignmentId),
                HttpStatus.OK
        );
    }

    @GetMapping("/announcement/{courseId}")
    public ResponseEntity<AnnouncementQueryMultipleResponse> getAnnouncements(@RequestHeader("Authorization") String token, @PathVariable Long courseId) {
        Integer id = jwtService.resolveToken(token);
        return new ResponseEntity<>(
                courseService.getAnnouncements(id, courseId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/module")
    public ResponseEntity<String> deleteModule(@RequestHeader("Authorization") String token, @RequestBody ModuleRequest module) {
        Integer id = jwtService.resolveToken(token);
        courseService.deleteModule(id, module.getModuleId());
        return new ResponseEntity<>(
                "Module successfully deleted from course",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/assignment")
    public ResponseEntity<String> deleteAssignment(@RequestHeader("Authorization") String token, @RequestBody AssignmentRequest assignment) {
        Integer id = jwtService.resolveToken(token);
        courseService.deleteAssignment(id, assignment.getAssignmentId());
        return new ResponseEntity<>(
                "Assignment successfully deleted from course",
                HttpStatus.OK
        );
    }
}
