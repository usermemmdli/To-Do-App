package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dto.request.LoginRequest;
import com.example.Task_Management_App.dto.request.SignUpRequest;
import com.example.Task_Management_App.security.JwtService;
import com.example.Task_Management_App.service.AuthService;
import com.example.Task_Management_App.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController authController;
    @Mock
    private UsersService usersService;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSignUp() throws Exception {
        //Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("admin");
        signUpRequest.setPassword("test123");
        signUpRequest.setEmail("admin@admin.com");

        // Act & Assert
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(authService, times(1)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    void login_ValidCredentials_ReturnsToken() throws Exception {
        // Arrange
        Users users = new Users();
        users.setUsername("admin");
        users.setPassword("test123");
        users.setEmail("admin@admin.com");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(users.getEmail());
        loginRequest.setPassword(users.getPassword());

        Authentication authentication = mock(Authentication.class);
        String expectedToken = "dummy.jwt.token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedToken))
                .andDo(print());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
