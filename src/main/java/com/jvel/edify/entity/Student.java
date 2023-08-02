package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.jvel.edify.entity.roles.Role;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(
        name = "student_table"
)
@DiscriminatorValue("STUDENT")
public class Student extends User {
    private String gender;
    private String address;
    private String phoneNumber;

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private List<Course> courses;

    @Builder
    public Student(String firstName, String lastName, String emailAddress, Integer ssn, String password, Date dob, String gender, String address, String phoneNumber) {
        super(firstName, lastName, emailAddress, ssn, password, dob, Role.STUDENT);
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.courses = new ArrayList<>();
    }

    @Builder
    public Student() {
        super();
    }
}
