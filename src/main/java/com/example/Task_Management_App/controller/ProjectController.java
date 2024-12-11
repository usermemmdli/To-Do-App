package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.ProjectRequest;
import com.example.Task_Management_App.dto.response.ProjectResponse;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import com.example.Task_Management_App.service.ProjectService;
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
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final AuthenticatedHelperService authenticatedHelperService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createProject(@RequestBody @Valid ProjectRequest projectRequest) {
        try {
            String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
            ResponseEntity<ProjectResponse> project = projectService.createProject(currentUserEmail, projectRequest);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
