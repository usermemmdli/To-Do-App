package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.TaskRequest;
import com.example.Task_Management_App.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskRequest taskRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        ResponseEntity<?> task = taskService.createTask(currentUserEmail, taskRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
