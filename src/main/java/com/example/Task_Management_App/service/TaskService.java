package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.TaskRepository;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.TaskRequest;
import com.example.Task_Management_App.dto.response.TaskResponse;
import com.example.Task_Management_App.enums.Priority;
import com.example.Task_Management_App.mapper.TaskMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UsersRepository usersRepository;

    public ResponseEntity<?> createTask(String currentUserEmail, @Valid TaskRequest taskRequest) {
        Users users = usersRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + currentUserEmail));
        Task task = taskMapper.toTask(taskRequest);
        task.setProject(Project);
        Task savedTask = taskRepository.save(task);
        TaskResponse taskResponse = taskMapper.toTaskResponse(savedTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

}
