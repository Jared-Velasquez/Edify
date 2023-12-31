package com.jvel.edify.controller.requests.user_requests.student_requests;

import com.jvel.edify.entity.enums.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MajorRequest {
    private String major;
}
