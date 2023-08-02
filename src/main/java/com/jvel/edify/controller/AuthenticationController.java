package com.jvel.edify.controller;

import com.jvel.edify.service.AuthenticationService;
import com.jvel.edify.controller.requests.AuthenticationRequest;
import com.jvel.edify.controller.requests.RegisterRequest;
import com.jvel.edify.controller.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse authResponse = service.register(request);
        return new ResponseEntity<>(
                authResponse,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse authResponse = service.authenticate(request);
        return new ResponseEntity<>(
                authResponse,
                HttpStatus.OK
        );
    }
}
