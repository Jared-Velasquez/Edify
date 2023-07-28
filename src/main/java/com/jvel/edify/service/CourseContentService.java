package com.jvel.edify.service;

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
    public void addCourseContent(String contentURL, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new IllegalArgumentException("no course found by id " + courseId);

        Course course = courseOptional.get();
        if (course.getCourseContent() != null)
            throw new IllegalArgumentException("course already has content");

        CourseContent newCourseContent = CourseContent.builder()
                .url(contentURL)
                .course(course)
                .build();
        courseContentRepository.save(newCourseContent);
    }

    public CourseContent getCourseContent(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new IllegalArgumentException("no course found by id " + courseId);
        return courseOptional.get().getCourseContent();
    }

    @Transactional
    public void deleteCourseContent(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new IllegalArgumentException("no course found by id " + courseId);

        Course course = courseOptional.get();
        if (course.getCourseContent() == null)
            throw new IllegalArgumentException("course does not have content to delete");

        CourseContent courseContent = course.getCourseContent();
        courseContentRepository.delete(courseContent);
    }
}
