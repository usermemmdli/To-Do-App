package com.example.Task_Management_App.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDeleteRequest {
    @NotNull
    private String password;
}
