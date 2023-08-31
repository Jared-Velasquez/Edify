package com.jvel.edify.controller.responses.course_responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCourseQueryResponse {
    private Long courseId;
    private String title;
}
