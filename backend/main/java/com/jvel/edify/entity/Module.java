package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "module_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "module_unique_in_course",
                        columnNames= {"title", "course_id"}
                )
        }
)
public class Module {
    @Id
    @SequenceGenerator(
            name = "module_sequence",
            sequenceName = "module_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "module_sequence"
    )
    private Integer moduleId;
    @Column(
            nullable = false
    )
    private String title;
    @OneToMany(
            mappedBy = "module"
    )
    private List<Assignment> assignments;
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
