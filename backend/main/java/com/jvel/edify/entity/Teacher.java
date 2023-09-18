package com.jvel.edify.entity;

import com.jvel.edify.entity.enums.Department;
import com.jvel.edify.entity.enums.Gender;
import com.jvel.edify.entity.enums.Position;
import com.jvel.edify.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(
        name = "teacher_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "departmental_email_unique",
                        columnNames = "departmentalEmail"
                )
        }
)
@DiscriminatorValue("TEACHER")
public class Teacher extends User {
    @Enumerated(EnumType.STRING)
    private Department department;
    @Enumerated(EnumType.STRING)
    private Position position;
    private String departmentalEmail;
    @Builder
    public Teacher(String firstName,
                   String lastName,
                   String username,
                   Integer ssn, String password,
                   Date dob,
                   Gender gender,
                   String address,
                   String phoneNumber) {
        super(firstName, lastName, username, ssn, password, dob, Role.TEACHER, gender, address, phoneNumber);
        this.departmentalEmail = this.getUniversityEmail();
    }

    @Builder
    public Teacher() {
        super();
    }
}
