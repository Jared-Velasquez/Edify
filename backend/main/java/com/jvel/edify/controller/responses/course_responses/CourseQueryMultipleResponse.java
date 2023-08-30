package com.jvel.edify.controller.responses.course_responses;

import com.jvel.edify.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseQueryMultipleResponse {
    private List<Course> courses;
}
