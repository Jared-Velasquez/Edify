package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "course_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="title_unique",
                        columnNames = "title"
                ),
                @UniqueConstraint(
                        name="code_unique",
                        columnNames = "code"
                ),
        }
)
@ToString(exclude = "students")
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long courseId;
    private String title;
    private String code;
    private boolean publiclyVisible;
    private Integer units;
    private String syllabusBody;

    @OneToOne(
            mappedBy = "course"
    )
    private CourseContent courseContent;

    // Consider deletion of CascadeType
    @ManyToMany
    @JoinTable(
            name = "student_course_map",
            joinColumns = @JoinColumn(
                    name = "course_id",
                    referencedColumnName = "courseId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "id"
            )
    )
    @JsonIgnore
    private List<Student> students;

    public void addStudents(Student student) {
        if (students == null) students = new ArrayList<>();
        students.add(student);
    }

    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Teacher teacher;
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.REMOVE
    )
    private List<Module> modules;
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.REMOVE
    )
    private List<Announcement> announcements;
}
