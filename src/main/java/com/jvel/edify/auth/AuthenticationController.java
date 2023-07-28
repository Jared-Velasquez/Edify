package com.jvel.edify.auth;

import com.jvel.edify.auth.requests.AuthenticationRequest;
import com.jvel.edify.auth.requests.RegisterRequest;
import com.jvel.edify.auth.responses.AuthenticationResponse;
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
        try {
            return new ResponseEntity<>(
                    service.register(request),
                    HttpStatus.CREATED
            );
        } catch (IllegalArgumentException iae) {
            System.out.println("iae = " + iae);
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            System.out.println("e = " + e);
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
