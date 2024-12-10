package com.example.Task_Management_App.dto.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private Timestamp updatedAt;
}
