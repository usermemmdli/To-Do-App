package com.example.Task_Management_App.dto.request;

import com.example.Task_Management_App.enums.Priority;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaskPriorityRequest {
    private Long taskId;
    private Priority priority;
    private Timestamp updatedAt;
}
