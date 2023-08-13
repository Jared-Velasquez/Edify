package com.jvel.edify.controller.responses;

import com.jvel.edify.entity.Student;
import com.jvel.edify.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryMultipleResponse {
    private List<User> users;
}