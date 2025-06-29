package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.TaskCompletedRequest;
import com.example.Task_Management_App.dto.request.TaskEditRequest;
import com.example.Task_Management_App.dto.request.TaskPriorityRequest;
import com.example.Task_Management_App.dto.request.TaskRequest;
import com.example.Task_Management_App.dto.response.TaskResponse;
import com.example.Task_Management_App.enums.Priority;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import com.example.Task_Management_App.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TaskController.class})
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;
    @MockBean
    private AuthenticatedHelperService authenticatedHelperService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testCreateTask() throws Exception {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("task title");
        taskRequest.setDescription("task description");
        taskRequest.setCompleted(true);
        taskRequest.setPriority(Priority.High);
        taskRequest.setProjectId(1L);

        TaskResponse taskResponse = TaskResponse.builder()
                .id(taskRequest.getProjectId())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .completed(taskRequest.getCompleted())
                .priority(String.valueOf(taskRequest.getPriority()))
                .build();

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn("user@example.com");
        when(taskService.createTask(anyString(), any(TaskRequest.class))).thenReturn(taskResponse);

        mockMvc.perform(post("/api/task/create")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(taskResponse)));

        verify(taskService).createTask(anyString(), any(TaskRequest.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testSetTaskPriority() throws Exception {
        TaskPriorityRequest taskPriorityRequest = new TaskPriorityRequest();
        taskPriorityRequest.setTaskId(1L);
        taskPriorityRequest.setPriority(Priority.High);

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn("user@example.com");

        mockMvc.perform(patch("/api/task/set-priority")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskPriorityRequest)))
                .andExpect(status().isCreated());

        verify(taskService).setTaskPriority(anyString(), any(TaskPriorityRequest.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testSetCompleted() throws Exception {
        TaskCompletedRequest taskCompletedRequest = new TaskCompletedRequest();
        taskCompletedRequest.setTaskId(1L);
        taskCompletedRequest.setCompleted(true);

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn("user@example.com");

        mockMvc.perform(patch("/api/task/set-completed")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskCompletedRequest)))
                .andExpect(status().isCreated());

        verify(taskService).setCompletedTask(anyString(), any(TaskCompletedRequest.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testEditTask() throws Exception {
        TaskEditRequest taskEditRequest = new TaskEditRequest();
        taskEditRequest.setTaskId(1L);
        taskEditRequest.setTitle("task title");
        taskEditRequest.setDescription("task description");

        TaskResponse taskResponse = TaskResponse.builder()
                .id(taskEditRequest.getTaskId())
                .title(taskEditRequest.getTitle())
                .description(taskEditRequest.getDescription())
                .build();

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn("user@example.com");
        when(taskService.editTask(anyString(), any(TaskEditRequest.class))).thenReturn(taskResponse);

        mockMvc.perform(put("/api/task/edit-task")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskEditRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(taskResponse)));

        verify(taskService).editTask(anyString(), any(TaskEditRequest.class));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testDeleteTask() throws Exception {
        Long taskId = 1L;
        String currentUserEmail = "user@example.com";

        when(authenticatedHelperService.getCurrentUserEmail()).thenReturn(currentUserEmail);

        mockMvc.perform(delete("/api/task/delete-task/{id}", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(currentUserEmail, taskId);
    }
}
