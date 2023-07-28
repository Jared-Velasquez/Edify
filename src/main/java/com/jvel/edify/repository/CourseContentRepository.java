package com.jvel.edify.repository;

import com.jvel.edify.entity.Course;
import com.jvel.edify.entity.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {

}
