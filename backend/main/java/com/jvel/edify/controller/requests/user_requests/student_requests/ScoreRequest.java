package com.jvel.edify.controller.requests.user_requests.student_requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRequest {
    private Integer score;
    private Integer assignmentId;
}
