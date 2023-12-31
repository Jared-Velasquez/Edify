package com.jvel.edify.repository;

import com.jvel.edify.entity.Assignment;
import com.jvel.edify.entity.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

}
