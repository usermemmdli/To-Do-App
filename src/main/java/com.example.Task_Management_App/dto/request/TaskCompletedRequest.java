package com.example.Task_Management_App.dto.request;

import lombok.Data;

@Data
public class TaskCompletedRequest {
    private Long taskId;
    private Boolean completed;
}
