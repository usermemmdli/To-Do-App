package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Role;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.RoleRepository;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.LoginRequest;
import com.example.Task_Management_App.dto.request.SignUpRequest;
import com.example.Task_Management_App.dto.response.JwtResponse;
import com.example.Task_Management_App.exception.InvalidEmailException;
import com.example.Task_Management_App.exception.InvalidEmailOrPasswordException;
import com.example.Task_Management_App.exception.RoleNotFoundException;
import com.example.Task_Management_App.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public void signUpUser(SignUpRequest signUpRequest) {
        if (usersRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new InvalidEmailException("Email is already taken");
        }
        Users users = new Users();

        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RoleNotFoundException("Default role not found"));

        users.getRoles().add(defaultRole);
        users.setUsername(signUpRequest.getUsername());
        users.setEmail(signUpRequest.getEmail());
        users.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        users.setCreatedAt(Timestamp.from(Instant.now()));
        usersRepository.save(users);
    }

    public JwtResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Users users = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidEmailOrPasswordException("Email or password is invalid"));
        String accessToken = jwtService.createAccessToken(users);
        String refreshToken = jwtService.createRefreshToken(users);

        return new JwtResponse(accessToken, refreshToken);
    }
}
