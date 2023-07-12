package com.jvel.edify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "student_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="email_address_unique",
                        columnNames = "emailAddress"
                ),
                @UniqueConstraint(
                        name = "ssn_unique",
                        columnNames = "ssn"
                )
        }
)
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long studentId;
    private String firstName;
    private String lastName;
    @Column(
            nullable = false
    )
    private String emailAddress;
    private String gender;
    private Date DOB;
    private String address;
    private String phoneNumber;
    @Column(
            nullable = false
    )
    private Integer SSN;
}
