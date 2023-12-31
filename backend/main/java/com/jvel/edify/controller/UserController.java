package com.jvel.edify.controller;

import com.jvel.edify.config.JwtService;
import com.jvel.edify.controller.requests.user_requests.EmailRequest;
import com.jvel.edify.controller.requests.user_requests.NameRequest;
import com.jvel.edify.controller.responses.user_responses.UserQueryMultipleResponse;
import com.jvel.edify.controller.responses.user_responses.UserQueryResponse;
import com.jvel.edify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<UserQueryResponse> getUser(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        UserQueryResponse response = userService.getUserById(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserQueryResponse> getUserById(@PathVariable("id") Integer id) {
        UserQueryResponse response = userService.getUserById(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }
    @GetMapping("/all")
    public ResponseEntity<UserQueryMultipleResponse> getAllUsers() {
        return new ResponseEntity<>(
                userService.getAllUsers(),
                HttpStatus.OK
        );
    }

    /*@PutMapping("/email")
    public ResponseEntity<Map<String, String>> updateEmail(@RequestHeader("Authorization") String token, @RequestBody EmailRequest emailRequest) {
        Integer id = jwtService.resolveToken(token);
        userService.updateEmail(id, emailRequest.getEmailAddress());
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Email successfully updated");
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }*/

    @PutMapping("/name")
    public ResponseEntity<Map<String, String>> updateName(@RequestHeader("Authorization") String token, @RequestBody NameRequest nameRequest) {
        Integer id = jwtService.resolveToken(token);
        userService.updateName(id, nameRequest.getFirstName(), nameRequest.getLastName());
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Name successfully updated");
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteUser(@RequestHeader("Authorization") String token) {
        Integer id = jwtService.resolveToken(token);
        userService.deleteUserById(id);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "User successfully deleted");
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }
}
