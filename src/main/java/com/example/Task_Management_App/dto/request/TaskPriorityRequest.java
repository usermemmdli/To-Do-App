package com.example.Task_Management_App.dto.request;

import com.example.Task_Management_App.enums.Priority;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class TaskPriorityRequest {
    private Long taskId;
    private Priority priority;
    private Timestamp updatedAt;
}
