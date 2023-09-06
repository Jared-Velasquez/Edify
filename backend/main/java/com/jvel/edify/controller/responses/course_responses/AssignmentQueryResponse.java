package com.jvel.edify.controller.responses.course_responses;

import com.jvel.edify.entity.Assignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentQueryResponse {
    private Assignment assignment;
}
