package com.example.Task_Management_App.dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UsersResponse {
    private String username;
    private String email;
    private Timestamp createdAt;
}
