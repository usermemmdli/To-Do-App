package com.example.Task_Management_App.dto.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProjectRequest {
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
