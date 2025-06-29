package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.ProjectRepository;
import com.example.Task_Management_App.dto.request.ProjectEditRequest;
import com.example.Task_Management_App.dto.request.ProjectRequest;
import com.example.Task_Management_App.dto.response.ProjectResponse;
import com.example.Task_Management_App.exception.ProjectCannotDeletedException;
import com.example.Task_Management_App.exception.ProjectNotFoundException;
import com.example.Task_Management_App.mapper.ProjectMapper;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final AuthenticatedHelperService authenticatedHelperService;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    public ProjectResponse createProject(String currentUserEmail, ProjectRequest projectRequest) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setUsers(users);
        project.setCreatedAt(Timestamp.from(Instant.now()));
        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    public ProjectResponse editProject(String currentUserEmail, ProjectEditRequest projectEditRequest) {
        authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        return projectRepository.findById(projectEditRequest.getProjectId())
                .map(project -> {
                    project.setName(projectEditRequest.getName());
                    project.setDescription(projectEditRequest.getDescription());
                    project.setUpdatedAt(Timestamp.from(Instant.now()));
                    return projectMapper.toProjectResponse(projectRepository.save(project));
                }).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }

    public void deleteProject(String currentUserEmail, Long id) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        if (!project.getUsers().getId().equals(users.getId())) {
            throw new ProjectCannotDeletedException("Project cannot be deleted! Project does not belong to the user");
        }
        projectRepository.deleteById(id);
    }
}
