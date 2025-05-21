package com.example.Task_Management_App.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SignUpRequest {
    private String username;
    @Email
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Please enter a valid email address"
    )
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    private Boolean isActive;
    private Timestamp createdAt;
}