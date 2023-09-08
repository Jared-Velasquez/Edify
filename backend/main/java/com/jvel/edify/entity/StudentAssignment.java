package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentAssignment {
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
    private Integer studentAssignmentId;

    @ManyToOne(
            optional = false
    )
    @JoinColumn(name = "studentId")
    @JsonIgnore
    private Student student;

    @ManyToOne(
            optional = false
    )
    @JoinColumn(name = "assignmentId")
    @JsonIgnore
    private Assignment assignment;

    private Integer points;
}
