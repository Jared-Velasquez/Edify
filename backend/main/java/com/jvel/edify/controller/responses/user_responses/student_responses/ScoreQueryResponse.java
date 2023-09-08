package com.jvel.edify.controller.responses.user_responses.student_responses;

import com.jvel.edify.entity.Assignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreQueryResponse {
    private Assignment assignment;
    private Integer score;
}
