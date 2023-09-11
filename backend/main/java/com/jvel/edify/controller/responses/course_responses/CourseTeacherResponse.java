package com.jvel.edify.controller.responses.course_responses;

import com.jvel.edify.entity.Announcement;
import com.jvel.edify.entity.CourseContent;
import com.jvel.edify.entity.Module;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseTeacherResponse {
    private Long courseId;
    private String title;
    private String code;
    private boolean publiclyVisible;
    private Integer units;
    private CourseContent courseContent;
    private List<Module> modules;
    private List<Announcement> announcements;
    private String firstName;
    private String lastName;
}
