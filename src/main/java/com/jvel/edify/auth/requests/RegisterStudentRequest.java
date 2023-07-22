package com.jvel.edify.auth.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStudentRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Integer ssn;
    private Date dob;
    private String gender;
    private String address;
    private String phoneNumber;
    private String password;
}
