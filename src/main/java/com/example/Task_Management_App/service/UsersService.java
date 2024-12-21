package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.UserEditRequest;
import com.example.Task_Management_App.dto.response.UsersResponse;
import com.example.Task_Management_App.mapper.UsersMapper;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final AuthenticatedHelperService authenticatedHelperService;

    public UsersResponse editUser(String currentUserEmail, UserEditRequest userEditRequest) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        users.setUsername(userEditRequest.getUsername());
        users.setEmail(userEditRequest.getEmail());
        users.setUpdatedAt(Timestamp.from(Instant.now()));
        Users savedUser = usersRepository.save(users);
        return usersMapper.toUsersResponse(savedUser);
    }
}