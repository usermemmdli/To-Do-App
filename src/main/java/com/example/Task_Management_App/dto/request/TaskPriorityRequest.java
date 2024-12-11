package com.example.Task_Management_App.dto.request;

import com.example.Task_Management_App.enums.Priority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskPriorityRequest {
    @Enumerated(EnumType.STRING)
    private Priority priority;
}
