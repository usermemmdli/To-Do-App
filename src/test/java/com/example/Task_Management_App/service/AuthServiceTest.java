package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Role;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.RoleRepository;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.LoginRequest;
import com.example.Task_Management_App.dto.request.SignUpRequest;

import com.example.Task_Management_App.dto.response.JwtResponse;
import com.example.Task_Management_App.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtService jwtService;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testSignUpUser() {
        // Arrange
        SignUpRequest request = new SignUpRequest();
        request.setUsername("admin");
        request.setEmail("admin@admin.com");
        request.setPassword("admin");

        Role role = new Role();
        role.setName("USER");

        when(usersRepository.existsByEmail("admin@admin.com")).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("admin")).thenReturn("encodedPassword");

        // Act
        authService.signUpUser(request);

        // Assert
        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        verify(usersRepository, times(1)).save(captor.capture());

        Users savedUser = captor.getValue();
        assertEquals("admin", savedUser.getUsername());
        assertEquals("admin@admin.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(role, savedUser.getRoles());
        assertNotNull(savedUser.getCreatedAt());
    }

    @Test
    void testLoginUser() {
        //Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("password");

        Users user = new Users();
        user.setEmail("user@example.com");
        user.setUsername("testuser");

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken("user@example.com", "password");

        when(authenticationManager.authenticate(any(org.springframework.security.core.Authentication.class)))
                .thenReturn(token);

        when(usersRepository.findByEmail("user@example.com"))
                .thenReturn(Optional.of(user));

        when(jwtService.createAccessToken(user))
                .thenReturn("access-token");

        when(jwtService.createRefreshToken(user))
                .thenReturn("refresh-token");
        // Act
        JwtResponse response = authService.loginUser(loginRequest);

        // Assert
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
    }
}
