package com.jvel.edify.repository;

import com.jvel.edify.entity.Course;
import com.jvel.edify.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByTitle(String title);
    List<Course> findByTeacher(Teacher teacher);
    Optional<Course> findByCode(String code);
}
