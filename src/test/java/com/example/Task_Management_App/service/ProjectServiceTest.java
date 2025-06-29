package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.ProjectRepository;
import com.example.Task_Management_App.dto.request.ProjectEditRequest;
import com.example.Task_Management_App.dto.request.ProjectRequest;
import com.example.Task_Management_App.dto.response.ProjectResponse;
import com.example.Task_Management_App.mapper.ProjectMapper;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectServiceTest {
    @InjectMocks
    private ProjectService projectService;
    @Mock
    private AuthenticatedHelperService authenticatedHelperService;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;

    @Test
    void testCreateProject() {
        // Given
        String currentUserEmail = "admin@example.com";

        ProjectRequest request = new ProjectRequest();
        request.setName("Test Project");
        request.setDescription("Test Description");

        Users user = new Users();
        user.setEmail(currentUserEmail);

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setUsers(user);

        ProjectResponse response = ProjectResponse.builder()
                .id(1L)
                .name("Test Project")
                .description("Test Description")
                .build();

        // When
        when(authenticatedHelperService.getAuthenticatedUser(currentUserEmail)).thenReturn(user);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapper.toProjectResponse(any(Project.class))).thenReturn(response);

        // Then
        ProjectResponse result = projectService.createProject(currentUserEmail, request);

        assertEquals("Test Project", result.getName());
        assertEquals("Test Description", result.getDescription());

        verify(authenticatedHelperService).getAuthenticatedUser(currentUserEmail);
        verify(projectRepository).save(any(Project.class));
        verify(projectMapper).toProjectResponse(any(Project.class));
    }

    @Test
    void testEditProject() {
        // Given
        String currentUserEmail = "admin@example.com";

        ProjectEditRequest editRequest = new ProjectEditRequest();
        editRequest.setProjectId(1L);
        editRequest.setName("Updated Project");
        editRequest.setDescription("Updated Description");

        Project existingProject = new Project();
        existingProject.setId(1L);
        existingProject.setName("Old Name");
        existingProject.setDescription("Old Description");

        Project updatedProject = new Project();
        updatedProject.setId(editRequest.getProjectId());
        updatedProject.setName(editRequest.getName());
        updatedProject.setDescription(editRequest.getDescription());

        ProjectResponse response = ProjectResponse.builder()
                .id(1L)
                .name("Updated Project")
                .description("Updated Description")
                .build();

        // When
        when(authenticatedHelperService.getAuthenticatedUser(currentUserEmail)).thenReturn(new Users());

        when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(any(Project.class))).thenReturn(updatedProject);
        when(projectMapper.toProjectResponse(updatedProject)).thenReturn(response);

        ProjectResponse result = projectService.editProject(currentUserEmail, editRequest);

        // Then
        assertEquals("Updated Project", result.getName());
        assertEquals("Updated Description", result.getDescription());

        verify(authenticatedHelperService).getAuthenticatedUser(currentUserEmail);
        verify(projectRepository).findById(1L);
        verify(projectRepository).save(any(Project.class));
        verify(projectMapper).toProjectResponse(updatedProject);
    }

    @Test
    void testDeleteProject() {
        String email = "user@example.com";
        Long projectId = 1L;

        Users user = new Users();
        user.setId(10L);

        Project project = new Project();
        project.setId(projectId);
        project.setUsers(user);

        when(authenticatedHelperService.getAuthenticatedUser(email)).thenReturn(user);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Method under test
        projectService.deleteProject(email, projectId);

        verify(authenticatedHelperService).getAuthenticatedUser(email);
        verify(projectRepository).findById(projectId);
        verify(projectRepository).deleteById(projectId);
    }
}
