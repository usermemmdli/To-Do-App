package com.example.Task_Management_App.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
public class UserResponse {
    private String username;
    private String email;
    private Timestamp createdAt;
}
