package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.UserChangePasswordRequest;
import com.example.Task_Management_App.dto.request.UserDeleteRequest;
import com.example.Task_Management_App.dto.request.UserEditRequest;
import com.example.Task_Management_App.dto.response.UserResponse;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import com.example.Task_Management_App.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsersService usersService;
    @MockBean
    private AuthenticatedHelperService authenticatedHelperService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testEditUser() throws Exception {
        UserEditRequest userEditRequest = new UserEditRequest();
        userEditRequest.setUsername("test username");
        userEditRequest.setEmail("test email");

        UserResponse userResponse = UserResponse.builder()
                .username(userEditRequest.getUsername())
                .email(userEditRequest.getEmail())
                .build();

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn("user@example.com");
        when(usersService.editUser(anyString(), any(UserEditRequest.class))).thenReturn(userResponse);

        mockMvc.perform(put("/api/users/edit-user")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEditRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponse)));

        verify(usersService, times(1)).editUser(anyString(), any(UserEditRequest.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testChangePassword() throws Exception {
        UserChangePasswordRequest userChangePasswordRequest = new UserChangePasswordRequest();
        userChangePasswordRequest.setOldPassword("old password");
        userChangePasswordRequest.setNewPassword("new password");

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn("user@example.com");

        mockMvc.perform(patch("/api/users/edit-user/change-password")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userChangePasswordRequest)))
                .andExpect(status().isOk());

        verify(usersService).changePassword(anyString(), any(UserChangePasswordRequest.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testDeleteUser() throws Exception {
        UserDeleteRequest userDeleteRequest = new UserDeleteRequest();
        userDeleteRequest.setPassword("password");

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn("user@example.com");

        mockMvc.perform(delete("/api/users/delete-user")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDeleteRequest)))
                .andExpect(status().isOk());

        verify(usersService, times(1)).deleteUser("user@example.com", userDeleteRequest);
    }
}
