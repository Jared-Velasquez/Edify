package com.jvel.edify.controller.requests.course_requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourse {
    private Long courseId;
    private String title;
    private String code;
    private Boolean publiclyVisible;
    private Integer units;
    private String syllabusBody;
}
