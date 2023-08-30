package com.jvel.edify.controller.requests.user_requests;

import com.jvel.edify.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Integer ssn;
    private Date dob;
    private Gender gender;
    private String address;
    private String phoneNumber;
    private String password;
    private String role;
}
