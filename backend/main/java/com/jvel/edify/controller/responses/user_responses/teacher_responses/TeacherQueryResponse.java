package com.jvel.edify.controller.responses.user_responses.teacher_responses;

import com.jvel.edify.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherQueryResponse {
    private User user;
}
