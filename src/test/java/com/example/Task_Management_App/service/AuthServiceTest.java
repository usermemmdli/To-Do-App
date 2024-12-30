package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.LoginRequest;
import com.example.Task_Management_App.dto.request.SignUpRequest;

import com.example.Task_Management_App.security.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AuthServiceTest {
    @Autowired
    private UsersRepository usersRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    @Test
    void testSignUpUser() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("admin");
        request.setEmail("admin@admin.com");
        request.setPassword("admin");

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        usersRepository.save(user);

        assertTrue(usersRepository.findByEmail(user.getEmail()).isPresent());
    }

    @Test
    void testLoginUserSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        Users user = new Users();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(usersRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(user));

        when(jwtService.createAccessToken(user)).thenReturn("mockAccessToken");
        when(jwtService.createRefreshToken(user)).thenReturn("mockRefreshToken");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usersRepository).findByEmail(loginRequest.getEmail());
        verify(jwtService).createAccessToken(user);
        verify(jwtService).createRefreshToken(user);
    }
}
