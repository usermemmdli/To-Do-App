package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.UserChangePasswordRequest;
import com.example.Task_Management_App.dto.request.UserEditRequest;
import com.example.Task_Management_App.dto.response.UserResponse;
import com.example.Task_Management_App.exception.InvalidPasswordException;
import com.example.Task_Management_App.exception.UserCannotDeletedException;
import com.example.Task_Management_App.mapper.UsersMapper;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticatedHelperService authenticatedHelperService;

    public UserResponse editUser(String currentUserEmail, UserEditRequest userEditRequest) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        users.setUsername(userEditRequest.getUsername());
        users.setEmail(userEditRequest.getEmail());
        users.setUpdatedAt(Timestamp.from(Instant.now()));
        Users savedUser = usersRepository.save(users);
        return usersMapper.toUsersResponse(savedUser);
    }

    public void changePassword(String currentUserEmail, UserChangePasswordRequest userChangePasswordRequest) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        if (userChangePasswordRequest.getOldPassword() != null &&
                passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), users.getPassword())) {
            users.setPassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
        } else {
            throw new InvalidPasswordException("Password does not match");
        }
        usersRepository.save(users);
    }

    public void deleteUser(String currentUserEmail, Long id) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        if (!users.getId().equals(id)) {
            throw new UserCannotDeletedException("User cannot be deleted! User does not belong to the user");
        }
        usersRepository.deleteById(id);
    }
}
