package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.ProjectRepository;
import com.example.Task_Management_App.dao.repository.TaskRepository;
import com.example.Task_Management_App.dto.request.TaskCompletedRequest;
import com.example.Task_Management_App.dto.request.TaskPriorityRequest;
import com.example.Task_Management_App.dto.request.TaskRequest;
import com.example.Task_Management_App.dto.response.TaskResponse;
import com.example.Task_Management_App.enums.Priority;
import com.example.Task_Management_App.mapper.TaskMapper;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;
    @Mock
    private AuthenticatedHelperService authenticatedHelperService;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;

    @Test
    void testCreateTask() {
        String email = "user@example.com";

        TaskRequest request = new TaskRequest();
        request.setProjectId(1L);
        request.setTitle("Test Task");
        request.setPriority(null);

        Users user = new Users();
        user.setId(10L);
        user.setEmail(email);

        Project project = new Project();
        project.setId(1L);
        project.setUsers(user);

        Task task = new Task();
        task.setTitle("Test Task");

        Task savedTask = new Task();
        savedTask.setId(100L);
        savedTask.setTitle("Test Task");
        savedTask.setPriority(Priority.Low);

        TaskResponse response = TaskResponse.builder()
                .id(task.getId())
                .title(savedTask.getTitle())
                .priority(String.valueOf(savedTask.getPriority()))
                .build();

        // Mocklar
        when(authenticatedHelperService.getAuthenticatedUser(email)).thenReturn(user);
        when(projectRepository.findByIdAndUsersId(1L, user.getId())).thenReturn(Optional.of(project));
        when(taskMapper.toTask(request)).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        when(taskMapper.toTaskResponse(savedTask)).thenReturn(response);

        // Servis call
        TaskResponse result = taskService.createTask(email, request);

        assertEquals("Test Task", result.getTitle());
        verify(authenticatedHelperService).getAuthenticatedUser(email);
        verify(projectRepository).findByIdAndUsersId(1L, user.getId());
        verify(taskMapper).toTask(request);
        verify(taskRepository).save(task);
        verify(taskMapper).toTaskResponse(savedTask);

        assertEquals(Priority.Low, task.getPriority());
    }

    @Test
    void testSetTaskPriority() {
        String email = "user@example.com";

        TaskPriorityRequest request = new TaskPriorityRequest();
        request.setTaskId(1L);
        request.setPriority(Priority.High);

        Users user = new Users();
        user.setId(5L);

        Task task = new Task();
        task.setId(1L);
        task.setPriority(Priority.Medium);

        Project project = new Project();
        project.setUsers(user);
        task.setProject(project);

        when(authenticatedHelperService.getAuthenticatedUser(email)).thenReturn(user);
        when(taskRepository.findByIdAndProject_UsersId((task.getId()), user.getId()))
                .thenReturn(Optional.of(task));

        taskService.setTaskPriority(email, request);

        assertEquals(Priority.High, task.getPriority());

        verify(taskRepository).save(task);
    }

    @Test
    void testSetCompletedPriority() {
        String email = "user@example.com";

        TaskCompletedRequest request = new TaskCompletedRequest();
        request.setTaskId(1L);
        request.setCompleted(true);

        Users user = new Users();
        user.setId(1L);

        Project project = new Project();
        project.setUsers(user);

        Task task = new Task();
        task.setId(1L);
        task.setCompleted(false);
        task.setProject(project);

        when(authenticatedHelperService.getAuthenticatedUser(email)).thenReturn(user);
        when(taskRepository.findByIdAndProject_UsersId(1L, 1L)).thenReturn(Optional.of(task));

        taskService.setCompletedTask(email, request);

        assertTrue(task.getCompleted());
        verify(taskRepository).save(task);
    }

    @Test
    void testEditTask() {
        String email = "user@example.com";
        Long taskId = 1L;

        Users user = new Users();
        user.setId(100L);
        user.setEmail(email);

        Project project = new Project();
        project.setUsers(user);

        Task task = new Task();
        task.setId(taskId);
        task.setProject(project);
        task.setCompleted(false);

        when(authenticatedHelperService.getAuthenticatedUser(email)).thenReturn(user);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.updateTask(email, taskId, t -> t.setCompleted(true));

        assertTrue(task.getCompleted());
        verify(taskRepository).save(task);
    }

    @Test
    void testDeleteTask() {
        String email = "user@example.com";
        Long taskId = 1L;

        Users user = new Users();
        user.setId(10L);

        Project project = new Project();
        project.setUsers(user);

        Task task = new Task();
        task.setId(taskId);
        task.setProject(project);

        when(authenticatedHelperService.getAuthenticatedUser(email)).thenReturn(user);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(email, taskId);

        verify(taskRepository).deleteById(taskId);
    }
}
