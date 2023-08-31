package com.jvel.edify.controller.responses.course_responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCourseQueryMultipleResponse {
    List<SimpleCourseQueryResponse> courses;
}