package com.example.Task_Management_App.dto.request;

import lombok.Data;

@Data
public class TaskEditRequest {
    private Long taskId;
    private String title;
    private String description;
}
