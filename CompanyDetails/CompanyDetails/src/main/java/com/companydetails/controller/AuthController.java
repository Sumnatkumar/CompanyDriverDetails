package com.companydetails.controller;

import com.companydetails.dto.LoginRequest;
import com.companydetails.dto.SignupRequest;
import com.companydetails.entities.User;
import com.companydetails.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            // Check if email already exists
            if (userService.existsByEmail(signupRequest.getEmail())) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Error: Email is already in use!");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Create new user
            User user = User.builder()
                    .firstName(signupRequest.getFirstName())
                    .lastName(signupRequest.getLastName())
                    .email(signupRequest.getEmail())
                    .password(signupRequest.getPassword())
                    .build();

            User savedUser = userService.saveUser(user);

            // Generate token
            String token = generateToken(savedUser);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "User registered successfully!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());

            if (userOptional.isEmpty() || !userService.checkPassword(loginRequest.getPassword(), userOptional.get().getPassword())) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Error: Invalid email or password!");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            User user = userOptional.get();
            String token = generateToken(user);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "User logged in successfully!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    private String generateToken(User user) {
        return "token-" + user.getId() + "-" + System.currentTimeMillis();
    }
}