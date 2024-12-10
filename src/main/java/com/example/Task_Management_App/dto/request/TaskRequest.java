package com.example.Task_Management_App.dto.request;

import com.example.Task_Management_App.enums.Priority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private Boolean completed;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private Timestamp updatedAt;
}
