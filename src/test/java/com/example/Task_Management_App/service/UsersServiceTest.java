package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.UserChangePasswordRequest;
import com.example.Task_Management_App.dto.request.UserDeleteRequest;
import com.example.Task_Management_App.dto.request.UserEditRequest;
import com.example.Task_Management_App.dto.response.UserResponse;
import com.example.Task_Management_App.mapper.UsersMapper;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class UsersServiceTest {
    @InjectMocks
    private UsersService userService;
    @Mock
    private AuthenticatedHelperService authenticatedHelperService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private UsersMapper usersMapper;

    @Test
    void testEditUser() {
        String email = "user@example.com";

        UserEditRequest request = new UserEditRequest();
        request.setUsername("newusername");
        request.setEmail("newemail@example.com");

        Users existingUser = new Users();
        existingUser.setEmail(email);
        existingUser.setUsername("oldusername");
        existingUser.setId(1L);

        Users savedUser = new Users();
        savedUser.setId(1L);
        savedUser.setUsername("newusername");
        savedUser.setEmail("newemail@example.com");

        UserResponse response = UserResponse.builder()
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .createdAt(savedUser.getCreatedAt())
                .build();

        when(authenticatedHelperService.getAuthenticatedUser(email)).thenReturn(existingUser);
        when(usersRepository.save(any(Users.class))).thenReturn(savedUser);
        when(usersMapper.toUsersResponse(savedUser)).thenReturn(response);

        UserResponse result = userService.editUser(email, request);

        assertEquals("newusername", result.getUsername());
        assertEquals("newemail@example.com", result.getEmail());

        verify(authenticatedHelperService).getAuthenticatedUser(email);
        verify(usersRepository).save(any(Users.class));
        verify(usersMapper).toUsersResponse(savedUser);
    }

    @Test
    void testChangePassword() {
        String email = "user@example.com";

        UserChangePasswordRequest request = new UserChangePasswordRequest();
        request.setOldPassword("old123");
        request.setNewPassword("new456");

        Users user = new Users();
        user.setEmail(email);
        user.setPassword("hashedOld123");

        // Eski şifre doğru eşleşiyor
        when(authenticatedHelperService.getAuthenticatedUser(email)).thenReturn(user);
        when(passwordEncoder.matches("old123", "hashedOld123")).thenReturn(true);
        when(passwordEncoder.encode("new456")).thenReturn("hashedNew456");

        userService.changePassword(email, request);

        // assert
        assertEquals("hashedNew456", user.getPassword());

        // verify
        verify(usersRepository).save(user);
    }

    @Test
    void testDeleteUser() {
        String email = "user@example.com";

        UserDeleteRequest request = new UserDeleteRequest();
        request.setPassword("correctPassword");

        Users user = new Users();
        user.setEmail(email);
        user.setPassword("hashedPassword");

        when(authenticatedHelperService.getAuthenticatedUser(email)).thenReturn(user);
        when(passwordEncoder.matches("correctPassword", "hashedPassword")).thenReturn(true);

        // Method call
        userService.deleteUser(email, request);

        // Verify
        verify(authenticatedHelperService).getAuthenticatedUser(email);
        verify(passwordEncoder).matches("correctPassword", "hashedPassword");
        verify(usersRepository).delete(user);
    }
}
