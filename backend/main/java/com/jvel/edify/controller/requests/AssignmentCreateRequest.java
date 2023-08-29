package com.jvel.edify.controller.requests;

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
    private Date dueAt;
    private Date unlockAt;
    private Date lockAt;
    private Integer pointsPossible;
    private boolean visible;
    private Integer moduleId;
}
