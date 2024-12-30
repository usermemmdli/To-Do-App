package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.ProjectRepository;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.ProjectEditRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Test
    void testCreateProject() {
        Users user = new Users();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("admin@admin.com");
        usersRepository.save(user);

        Project project = new Project();
        project.setName("test");
        project.setDescription("test");
        project.setUsers(user);
        projectRepository.save(project);

        assertTrue(projectRepository.existsById(project.getId()));
    }

    @Test
    void testEditProject() {
        Users user = new Users();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("admin@admin.com");
        usersRepository.save(user);

        Project project = new Project();
        project.setName("test");
        project.setDescription("test");
        project.setUsers(user);
        projectRepository.save(project);

        ProjectEditRequest projectEditRequest = new ProjectEditRequest();
        projectEditRequest.setName("test request");
        projectEditRequest.setDescription("test request");

        project.setName(projectEditRequest.getName());
        project.setDescription(projectEditRequest.getDescription());
        projectRepository.save(project);

        Project updatedProject = projectRepository.findById(project.getId()).orElse(null);

        assertThat(updatedProject).isNotNull();
        assertThat(updatedProject.getName()).isEqualTo("test request");
        assertThat(updatedProject.getDescription()).isEqualTo("test request");
        assertThat(updatedProject.getUsers().getUsername()).isEqualTo("admin");
    }

    @Test
    void testDeleteProject() {
        Users user = new Users();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("admin@admin.com");
        usersRepository.save(user);

        Project project = new Project();
        project.setName("test");
        project.setDescription("test");
        project.setUsers(user);
        projectRepository.save(project);

        projectService.deleteProject(user.getEmail(), project.getId());

        assertFalse(projectRepository.existsById(project.getId()));
    }
}
