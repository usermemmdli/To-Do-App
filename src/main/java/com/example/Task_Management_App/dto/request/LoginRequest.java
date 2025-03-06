package com.example.Task_Management_App.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}