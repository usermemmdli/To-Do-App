package com.example.Task_Management_App.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaskResponse {
    private String title;
    private String description;
    private Boolean completed;
    private String priority;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
