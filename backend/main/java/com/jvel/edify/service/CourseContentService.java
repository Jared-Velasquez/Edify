package com.jvel.edify.service;

import com.jvel.edify.controller.exceptions.ContentAlreadyExistsException;
import com.jvel.edify.controller.exceptions.ContentNotFoundException;
import com.jvel.edify.controller.exceptions.CourseNotFoundException;
import com.jvel.edify.controller.exceptions.UnauthorizedAccessException;
import com.jvel.edify.entity.Course;
import com.jvel.edify.entity.CourseContent;
import com.jvel.edify.repository.CourseContentRepository;
import com.jvel.edify.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseContentService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseContentRepository courseContentRepository;
    public void addCourseContent(Integer id, String contentURL, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);

        Course course = courseOptional.get();
        // Check if id is the id of the teacher of this course
        System.out.println("teacher id: " + course.getTeacher().getId());
        System.out.println("given id: " + id);
        if (!course.getTeacher().getId().equals(id))
            throw new UnauthorizedAccessException("User is not teacher of course " + courseId);

        if (course.getCourseContent() != null)
            throw new ContentAlreadyExistsException("Course already contains content");

        CourseContent newCourseContent = CourseContent.builder()
                .url(contentURL)
                .course(course)
                .build();
        courseContentRepository.save(newCourseContent);
    }

    public CourseContent getCourseContent(Integer id, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);

        return courseOptional.get().getCourseContent();
    }

    @Transactional
    public void deleteCourseContent(Integer id, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);

        Course course = courseOptional.get();
        if (!course.getTeacher().getId().equals(id))
            throw new UnauthorizedAccessException("User is not teacher of course " + courseId);

        if (course.getCourseContent() == null)
            throw new ContentNotFoundException("Course does not have content to delete");

        CourseContent courseContent = course.getCourseContent();
        courseContentRepository.delete(courseContent);
    }
}
