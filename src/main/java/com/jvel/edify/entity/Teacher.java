package com.jvel.edify.entity;

import com.jvel.edify.entity.roles.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Data
@Entity
@Table(
        name = "teacher_table"
)
@DiscriminatorValue("TEACHER")
public class Teacher extends User {
    private String gender;
    private String address;
    private String phoneNumber;

    @Builder
    public Teacher(String firstName, String lastName, String emailAddress, Integer ssn, String password, Date dob, String gender, String address, String phoneNumber) {
        super(firstName, lastName, emailAddress, ssn, password, dob, Role.TEACHER);
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public Teacher() {
        super();
    }
}
