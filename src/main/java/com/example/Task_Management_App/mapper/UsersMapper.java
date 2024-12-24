package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dto.request.UserEditRequest;
import com.example.Task_Management_App.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class UsersMapper {
    public static Users toUsers(UserEditRequest userEditRequest) {
        return Users.builder()
                .username(userEditRequest.getUsername())
                .email(userEditRequest.getEmail())
                .build();
    }

    public static UserResponse toUsersResponse(Users users) {
        return UserResponse.builder()
                .username(users.getUsername())
                .email(users.getEmail())
                .createdAt(users.getCreatedAt())
                .build();
    }
}
