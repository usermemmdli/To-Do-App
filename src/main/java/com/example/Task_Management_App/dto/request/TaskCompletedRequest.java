package com.example.Task_Management_App.dto.request;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class TaskCompletedRequest {
    private Long taskId;
    private Boolean completed;
    private Timestamp updatedAt;
}
