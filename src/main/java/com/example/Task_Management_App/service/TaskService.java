package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.ProjectRepository;
import com.example.Task_Management_App.dao.repository.TaskRepository;
import com.example.Task_Management_App.dto.request.TaskPriorityRequest;
import com.example.Task_Management_App.dto.request.TaskRequest;
import com.example.Task_Management_App.dto.response.TaskResponse;
import com.example.Task_Management_App.enums.Priority;
import com.example.Task_Management_App.mapper.TaskMapper;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final AuthenticatedHelperService authenticatedHelperService;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskResponse createTask(String currentUserEmail,
                                   @Valid TaskRequest taskRequest) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        Project project = projectRepository.findByIdAndUsersId(taskRequest.getProjectId(), users.getId())
                .orElseThrow(() -> new RuntimeException("Project not found or does not belong to the user"));

        Task task = taskMapper.toTask(taskRequest);
        task.setProject(project);
        if (taskRequest.getPriority() == null) {
            task.setPriority(Priority.Low);
        } else {
            task.setPriority(taskRequest.getPriority());
        }
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(savedTask);
    }

    public void setTaskPriority(String currentUserEmail,
                                        @Valid TaskPriorityRequest taskPriorityRequest) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        Task task = taskRepository.findById(taskPriorityRequest.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found or does not belong to the user"));

        task.setPriority(taskPriorityRequest.getPriority());
        task.setUpdatedAt(Timestamp.from(Instant.now()));
        Task updatedTask = taskRepository.save(task);
        taskMapper.toTaskResponse(updatedTask);
    }


}
