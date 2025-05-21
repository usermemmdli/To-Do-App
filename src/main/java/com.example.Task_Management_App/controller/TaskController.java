package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.TaskEditRequest;
import com.example.Task_Management_App.dto.request.TaskCompletedRequest;
import com.example.Task_Management_App.dto.request.TaskPriorityRequest;
import com.example.Task_Management_App.dto.request.TaskRequest;
import com.example.Task_Management_App.dto.response.TaskResponse;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import com.example.Task_Management_App.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final AuthenticatedHelperService authenticatedHelperService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest taskRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        TaskResponse taskResponse = taskService.createTask(currentUserEmail, taskRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @PatchMapping("/set-priority")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void setTaskPriority(@RequestBody @Valid TaskPriorityRequest taskPriorityRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        taskService.setTaskPriority(currentUserEmail, taskPriorityRequest);
    }

    @PatchMapping("/set-completed")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void setCompletedTask(@RequestBody @Valid TaskCompletedRequest taskCompletedRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        taskService.setCompletedTask(currentUserEmail, taskCompletedRequest);
    }

    @PutMapping("/edit-task")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskResponse> editTask(@RequestBody @Valid TaskEditRequest editTaskRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        TaskResponse taskResponse = taskService.editTask(currentUserEmail, editTaskRequest);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }

    @DeleteMapping("/delete-task/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable Long id) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        taskService.deleteTask(currentUserEmail, id);
    }
}
