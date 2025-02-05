package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.LoginRequest;
import com.example.Task_Management_App.dto.request.SignUpRequest;
import com.example.Task_Management_App.dto.response.JwtResponse;
import com.example.Task_Management_App.security.JwtService;
import com.example.Task_Management_App.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UsersRepository usersRepository;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        authService.signUpUser(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.loginUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || !JwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        String email = JwtService.extractUsername(refreshToken);

        if (email == null) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = JwtService.createAccessToken(users);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);

        return ResponseEntity.ok(response);
    }
}
