package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.ProjectRepository;
import com.example.Task_Management_App.dao.repository.TaskRepository;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.TaskCompletedRequest;
import com.example.Task_Management_App.dto.request.TaskEditRequest;
import com.example.Task_Management_App.dto.request.TaskPriorityRequest;
import com.example.Task_Management_App.enums.Priority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testCreateTask() {
        Users user = new Users();
        user.setUsername("admin");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user = usersRepository.save(user);

        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test");
        project.setUsers(user);
        projectRepository.save(project);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test");
        task.setCompleted(true);
        task.setPriority(Priority.High);
        task.setProject(project);
        task = taskRepository.save(task);

        assertTrue(taskRepository.findById(task.getId()).isPresent());
    }

    @Test
    void testSetTaskPriority() {
        Users user = new Users();
        user.setUsername("admin");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user = usersRepository.save(user);

        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test");
        project.setUsers(user);
        project = projectRepository.save(project);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test");
        task.setProject(project);
        task = taskRepository.save(task);

        TaskPriorityRequest request = new TaskPriorityRequest();
        request.setTaskId(task.getId());
        request.setPriority(Priority.High);

        task.setPriority(request.getPriority());
        task = taskRepository.save(task);

        Optional<Task> tasksWithHighPriority = taskRepository.findByPriority(Priority.High);
        assertFalse(tasksWithHighPriority.isEmpty(), "High priority tasks should exist");
        Task finalTask = task;
        assertTrue(tasksWithHighPriority.stream().anyMatch(t -> t.getId().equals(finalTask.getId())),
                "Saved task should have High priority");
    }

    @Test
    void testSetCompletedPriority() {
        Users user = new Users();
        user.setUsername("admin");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user = usersRepository.save(user);

        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test");
        project.setUsers(user);
        project = projectRepository.save(project);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test");
        task.setProject(project);
        task = taskRepository.save(task);

        TaskCompletedRequest request = new TaskCompletedRequest();
        request.setTaskId(task.getId());
        request.setCompleted(false);

        task.setCompleted(request.getCompleted());
        task = taskRepository.save(task);

        Optional<Task> taskWithFalseCompleted = taskRepository.findByCompleted(false);
        assertFalse(taskWithFalseCompleted.isEmpty(), "Tasks should exist");
        assertTrue(taskRepository.findById(task.getId()).isPresent());
    }

    @Test
    void testEditTask() {
        Users user = new Users();
        user.setUsername("admin");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user = usersRepository.save(user);

        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test");
        project.setUsers(user);
        project = projectRepository.save(project);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test");
        task.setProject(project);
        task = taskRepository.save(task);

        TaskEditRequest request = new TaskEditRequest();
        request.setTaskId(task.getId());
        request.setTitle("Test Request");
        request.setDescription("Request");

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task = taskRepository.save(task);

        assertTrue(taskRepository.findById(task.getId()).isPresent());
    }

    @Test
    void testDeleteTask() {
        Users user = new Users();
        user.setUsername("admin");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user = usersRepository.save(user);

        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test");
        project.setUsers(user);
        project = projectRepository.save(project);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test");
        task.setProject(project);
        task = taskRepository.save(task);

        taskService.deleteTask(user.getEmail(), task.getId());

        assertFalse(taskRepository.findById(task.getId()).isPresent());
    }
}
