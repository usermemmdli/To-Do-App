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
                .orElseThrow(() -> new RuntimeException("wdqd"));

        Project project = ProjectMapper.toProject(projectRequest);
        Project savedProject = projectRepository.save(project);
        ProjectMapper.toProjectResponse(savedProject);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
