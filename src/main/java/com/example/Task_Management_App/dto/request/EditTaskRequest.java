package com.example.Task_Management_App.dto.request;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class EditTaskRequest {
    private Long taskId;
    private String title;
    private String description;
    private Timestamp updatedAt;
}
