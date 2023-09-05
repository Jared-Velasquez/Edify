package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "announcement_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "announcement_unique_in_course",
                        columnNames= {"title", "course_id"}
                )
        }
)
public class Announcement {
    @Id
    @SequenceGenerator(
            name = "announcement_sequence",
            sequenceName = "announcement_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "announcement_sequence"
    )
    private Integer announcementId;
    @Column(
            nullable = false
    )
    private String title;
    @Column(
            nullable = false
    )
    private String description;
    @Column(
            nullable = false,
            columnDefinition="DATETIME"
    )
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "courseId"
    )
    @JsonIgnore
    private Course course;
}
