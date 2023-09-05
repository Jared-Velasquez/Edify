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
        name = "teacher_table"
)
@DiscriminatorValue("TEACHER")
public class Teacher extends User {
    @Enumerated(EnumType.STRING)
    private Department department;
    @Enumerated(EnumType.STRING)
    private Position position;
    @Builder
    public Teacher(String firstName,
                   String lastName,
                   String emailAddress,
                   Integer ssn, String password,
                   Date dob,
                   Gender gender,
                   String address,
                   String phoneNumber) {
        super(firstName, lastName, emailAddress, ssn, password, dob, Role.TEACHER, gender, address, phoneNumber);
    }

    @Builder
    public Teacher() {
        super();
    }
}
