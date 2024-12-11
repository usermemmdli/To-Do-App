package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.ProjectRepository;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.ProjectRequest;
import com.example.Task_Management_App.dto.response.ProjectResponse;
import com.example.Task_Management_App.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UsersRepository usersRepository;

    public ResponseEntity<?> createProject(String currentUserEmail, ProjectRequest projectRequest) {
        Users users = usersRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + currentUserEmail));

        Project project = projectMapper.toProject(projectRequest);
        project.setUsers(users);
        Project savedProject = projectRepository.save(project);
        ProjectResponse projectResponse = projectMapper.toProjectResponse(savedProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);
    }


}
