package com.example.Task_Management_App.dto.request;

import com.example.Task_Management_App.enums.Priority;
import lombok.Data;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private Boolean completed;
    private Priority priority;
    private Long projectId;
}
