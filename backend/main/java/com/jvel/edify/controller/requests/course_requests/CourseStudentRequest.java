package com.jvel.edify.controller.requests.course_requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudentRequest {
    private Long courseId;
    private Integer studentId;
}
