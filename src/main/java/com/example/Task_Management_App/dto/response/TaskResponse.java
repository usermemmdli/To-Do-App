package com.example.Task_Management_App.dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private String priority;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
