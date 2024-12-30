package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.ProjectRepository;
import com.example.Task_Management_App.dao.repository.TaskRepository;
import com.example.Task_Management_App.dto.request.TaskEditRequest;
import com.example.Task_Management_App.dto.request.TaskCompletedRequest;
import com.example.Task_Management_App.dto.request.TaskPriorityRequest;
import com.example.Task_Management_App.dto.request.TaskRequest;
import com.example.Task_Management_App.dto.response.TaskResponse;
import com.example.Task_Management_App.enums.Priority;
import com.example.Task_Management_App.exception.ProjectNotFoundException;
import com.example.Task_Management_App.exception.TaskCannotDeletedException;
import com.example.Task_Management_App.exception.TaskNotFoundException;
import com.example.Task_Management_App.mapper.TaskMapper;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.function.Consumer;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final AuthenticatedHelperService authenticatedHelperService;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskResponse createTask(String currentUserEmail,
                                   @Valid TaskRequest taskRequest) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        Project project = projectRepository.findByIdAndUsersId(taskRequest.getProjectId(), users.getId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found or does not belong to the user"));

        Task task = TaskMapper.toTask(taskRequest);
        task.setProject(project);
        if (taskRequest.getPriority() == null) {
            task.setPriority(Priority.Low);
        } else {
            task.setPriority(taskRequest.getPriority());
        }
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toTaskResponse(savedTask);
    }

    public void setTaskPriority(String currentUserEmail,
                                @Valid TaskPriorityRequest taskPriorityRequest) {
        updateTask(currentUserEmail, taskPriorityRequest.getTaskId(),
                task -> task.setPriority(taskPriorityRequest.getPriority()));
    }

    public void setCompletedTask(String currentUserEmail,
                                 @Valid TaskCompletedRequest taskCompletedRequest) {
        updateTask(currentUserEmail, taskCompletedRequest.getTaskId(),
                task -> task.setCompleted(taskCompletedRequest.getCompleted()));
    }

    private void updateTask(String currentUserEmail, Long id,
                            Consumer<Task> updateFunction) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found or does not belong to the user"));

        updateFunction.accept(task);
        task.setUpdatedAt(Timestamp.from(Instant.now()));
        Task updatedTask = taskRepository.save(task);
        TaskMapper.toTaskResponse(updatedTask);
    }

    public TaskResponse editTask(String currentUserEmail,
                                 @Valid TaskEditRequest editTaskRequest) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        return taskRepository.findById(editTaskRequest.getTaskId())
                .map(task -> {
                    if (editTaskRequest.getTitle() != null) {
                        task.setTitle(editTaskRequest.getTitle());
                    }
                    if (editTaskRequest.getDescription() != null) {
                        task.setDescription(editTaskRequest.getDescription());
                    }
                    task.setUpdatedAt(Timestamp.from(Instant.now()));
                    return TaskMapper.toTaskResponse(taskRepository.save(task));
                })
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public void deleteTask(String currentUserEmail, Long id) {
        Users users = authenticatedHelperService.getAuthenticatedUser(currentUserEmail);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found or does not belong to the user"));
        Project project = task.getProject();
        if (!project.getUsers().getId().equals(users.getId())) {
            throw new TaskCannotDeletedException("Task cannot be deleted! Task does not belong to the user");
        }
        taskRepository.deleteById(id);
    }
}
