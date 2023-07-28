package com.jvel.edify.service;

import com.jvel.edify.repository.CourseRepository;
import com.jvel.edify.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;

    public void addTeacherToCourse(Integer teacherId, Long courseId) {

    }
}
