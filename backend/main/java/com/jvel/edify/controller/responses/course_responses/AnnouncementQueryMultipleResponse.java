package com.jvel.edify.controller.responses.course_responses;

import com.jvel.edify.entity.Announcement;
import com.jvel.edify.entity.Assignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementQueryMultipleResponse {
    private List<Announcement> announcements;
}
