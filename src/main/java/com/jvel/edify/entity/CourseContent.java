package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jvel.edify.entity.Course;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "course_content_table"
)
@ToString(exclude = "course")
public class CourseContent {
    @Id
    @SequenceGenerator(
            name = "course_content_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_content_sequence"
    )
    private Long courseContentId;
    private String url;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "courseId"
    )
    @JsonIgnore
    private Course course;
}
