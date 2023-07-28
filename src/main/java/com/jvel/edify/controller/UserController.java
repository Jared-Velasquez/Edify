package com.jvel.edify.controller;

import com.jvel.edify.controller.responses.StudentQueryMultipleResponse;
import com.jvel.edify.controller.responses.UserQueryMultipleResponse;
import com.jvel.edify.entity.User;
import com.jvel.edify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/all")
    public ResponseEntity<UserQueryMultipleResponse> getAllStudents() {
        return new ResponseEntity<>(
                userService.getAllUsers(),
                HttpStatus.OK
        );
    }
}
