package com.jvel.edify.controller.requests.course_requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentCreateRequest {
    private String title;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date dueAt;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date unlockAt;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date lockAt;
    private Integer pointsPossible;
    private boolean visible;
    private Integer moduleId;
}
