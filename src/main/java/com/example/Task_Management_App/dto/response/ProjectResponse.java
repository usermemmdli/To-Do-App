package com.example.Task_Management_App.dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ProjectResponse {
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
