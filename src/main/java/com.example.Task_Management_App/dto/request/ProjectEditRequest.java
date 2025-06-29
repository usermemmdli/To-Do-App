package com.example.Task_Management_App.dto.request;

import lombok.Data;

@Data
public class ProjectEditRequest {
    private Long projectId;
    private String name;
    private String description;
}
