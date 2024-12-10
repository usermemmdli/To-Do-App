package com.example.Task_Management_App.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserResponse {
    private String username;
    private String email;
    private Timestamp createdAt;
}
