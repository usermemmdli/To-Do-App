package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.ProjectEditRequest;
import com.example.Task_Management_App.dto.request.ProjectRequest;
import com.example.Task_Management_App.dto.response.ProjectResponse;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import com.example.Task_Management_App.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProjectService projectService;
    @MockBean
    private AuthenticatedHelperService authenticatedHelperService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testCreateProject() throws Exception {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("test");
        projectRequest.setDescription("test");

        ProjectResponse projectResponse = ProjectResponse.builder()
                .id(1L)
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .build();

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn("user@example.com");
        when(projectService.createProject(anyString(), any(ProjectRequest.class))).thenReturn(projectResponse);

        mockMvc.perform(post("/api/project/create")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(projectResponse)));

        verify(projectService, times(1)).createProject(anyString(), any(ProjectRequest.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testEditProject() throws Exception {
        ProjectEditRequest projectEditRequest = new ProjectEditRequest();
        projectEditRequest.setProjectId(1L);
        projectEditRequest.setName("test");
        projectEditRequest.setDescription("test");

        ProjectResponse projectResponse = ProjectResponse.builder()
                .id(projectEditRequest.getProjectId())
                .name(projectEditRequest.getName())
                .description(projectEditRequest.getDescription())
                .build();

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn("user@example.com");
        when(projectService.editProject(anyString(), any(ProjectEditRequest.class))).thenReturn(projectResponse);

        mockMvc.perform(put("/api/project/edit-project")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectEditRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(projectResponse)));

        verify(projectService, times(1)).editProject(anyString(), any(ProjectEditRequest.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testDeleteProject() throws Exception {
        Long projectId = 1L;
        String currentUserEmail = "user@example.com";

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn(currentUserEmail);
        doNothing().when(projectService).deleteProject(currentUserEmail, projectId);

        mockMvc.perform(delete("/api/project/delete-project/{id}", projectId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        verify(projectService, times(1)).deleteProject(currentUserEmail, projectId);
    }
}
