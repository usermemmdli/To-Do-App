package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.ProjectEditRequest;
import com.example.Task_Management_App.dto.request.ProjectRequest;
import com.example.Task_Management_App.dto.response.ProjectResponse;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import com.example.Task_Management_App.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final AuthenticatedHelperService authenticatedHelperService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest projectRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        ProjectResponse project = projectService.createProject(currentUserEmail, projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PutMapping("/edit-project")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProjectResponse> editProject(@RequestBody @Valid ProjectEditRequest projectEditRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        ProjectResponse project = projectService.editProject(currentUserEmail, projectEditRequest);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @DeleteMapping("/delete-project/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@PathVariable Long id) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        projectService.deleteProject(currentUserEmail, id);
    }
}
