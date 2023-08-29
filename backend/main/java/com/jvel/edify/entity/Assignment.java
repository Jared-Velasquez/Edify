package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "assignment_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "assignment_unique_in_module",
                        columnNames= {"title", "module_id"}
                )
        }
)
public class Assignment {
    @Id
    @SequenceGenerator(
            name = "assignment_sequence",
            sequenceName = "assignment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "assignment_sequence"
    )
    private Integer assignmentId;
    @Column(
            nullable = false
    )
    private String title;
    private String description;
    @Column(
            nullable = false
    )
    private Date dueAt;
    private Date unlockAt;
    private Date lockAt;
    private Integer pointsPossible;
    @Column(
            nullable = false
    )
    private Date createdAt;
    @Column(
            nullable = false
    )
    private boolean visible;
    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "module_id",
            referencedColumnName = "moduleId"
    )
    @JsonIgnore
    private Module module;
}
