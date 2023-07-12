package com.jvel.edify.teacher;

import com.jvel.edify.course.Course;
import com.jvel.edify.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "teacher_table",
        uniqueConstraints = @UniqueConstraint(
                name="email_address_unique",
                columnNames = "emailAddress"
        )
)
public class Teacher {
    @Id
    @SequenceGenerator(
            name = "teacher_sequence",
            sequenceName = "teacher_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "teacher_sequence"
    )
    private Long teacherId;
    private String firstName;
    private String lastName;
    @Column(
            nullable = false
    )
    private String emailAddress;
}
