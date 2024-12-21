package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.ProjectRepository;
import com.example.Task_Management_App.dao.repository.TaskRepository;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
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
    @Transactional
    public void testDeleteTask() {
        // Kullanıcı ve Task oluştur
        Users user = new Users();
        user.setUsername("admin");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user = usersRepository.save(user);

        Project project = new Project();
        project.setName("Test Project");
        project.setUsers(user);
        projectRepository.save(project);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setProject(project);
        taskRepository.save(task);

        // Task'ı sil
        taskService.deleteTask(user.getEmail(), task.getId());

        // Task'ın silindiğini kontrol et
        assertFalse(taskRepository.findById(task.getId()).isPresent());
    }
}
