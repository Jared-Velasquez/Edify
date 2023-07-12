package com.jvel.edify.coursecontent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {
}
