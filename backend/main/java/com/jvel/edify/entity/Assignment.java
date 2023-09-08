package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueAt;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date unlockAt;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockAt;
    private Integer pointsPossible;
    @Column(
            nullable = false
    )
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
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
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StudentAssignment> studentAssignments;
    @Transient
    public void addStudent(StudentAssignment sa) {
        if (studentAssignments == null)
            studentAssignments = new ArrayList<>();
        studentAssignments.add(sa);
    }
}
