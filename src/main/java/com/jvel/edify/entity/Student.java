package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jvel.edify.entity.enums.Gender;
import com.jvel.edify.entity.enums.Major;
import com.jvel.edify.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

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
    @Enumerated(EnumType.STRING)
    private Major major;
    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private List<Course> courses;

    @Builder
    public Student(String firstName, String lastName, String emailAddress, Integer ssn, String password, Date dob, Gender gender, String address, String phoneNumber) {
        super(firstName, lastName, emailAddress, ssn, password, dob, Role.STUDENT, gender, address, phoneNumber);
        this.courses = new ArrayList<>();
    }

    @Builder
    public Student() {
        super();
    }
}
