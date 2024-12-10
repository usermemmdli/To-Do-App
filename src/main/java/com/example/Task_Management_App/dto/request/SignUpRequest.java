package com.example.Task_Management_App.dto.request;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private Boolean isActive;
    private Timestamp createdAt;
}