package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dto.response.UsersResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class UsersMapper {
    public Users toUsers(Users userEditRequest) {
        return Users.builder()
                .username(userEditRequest.getUsername())
                .email(userEditRequest.getEmail())
                .build();
    }

    public UsersResponse toUsersResponse(Users users) {
        return UsersResponse.builder()
                .username(users.getUsername())
                .email(users.getEmail())
                .createdAt(users.getCreatedAt())
                .build();
    }
}
