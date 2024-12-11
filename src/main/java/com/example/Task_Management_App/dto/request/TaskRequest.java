package com.example.Task_Management_App.dto.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private Boolean completed;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
