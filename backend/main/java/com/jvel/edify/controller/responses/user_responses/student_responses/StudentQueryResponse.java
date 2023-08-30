package com.jvel.edify.controller.responses.user_responses.student_responses;

import com.jvel.edify.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentQueryResponse {
    private User student;
}
