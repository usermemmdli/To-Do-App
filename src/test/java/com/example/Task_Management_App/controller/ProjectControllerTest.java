package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.ProjectRequest;
import com.example.Task_Management_App.dto.response.ProjectResponse;
import com.example.Task_Management_App.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {
    @MockBean
    private ProjectService projectService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProject() throws Exception {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("test");
        projectRequest.setDescription("test");
        projectRequest.setCreatedAt(Timestamp.from(Instant.now()));
        projectRequest.setUpdatedAt(Timestamp.from(Instant.now()));

        ProjectResponse projectResponse = ProjectResponse.builder()
                .id(1L)
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .createdAt(projectRequest.getCreatedAt())
                .updatedAt(projectRequest.getUpdatedAt())
                .build();

        String currentUserEmail = "user@example.com";

        when(projectService.createProject(eq(currentUserEmail), any(ProjectRequest.class)))
                .thenReturn(projectResponse);

        mockMvc.perform(post("/api/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(projectResponse)));
    }
}
