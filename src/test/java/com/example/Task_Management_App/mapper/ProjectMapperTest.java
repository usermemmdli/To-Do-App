package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dto.request.ProjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ProjectMapperTest {
    @Test
    void toProject() {
        //Arrange
        var request = new ProjectRequest();
        request.setName("test");
        request.setDescription("test");

        //Actual
        var actual = ProjectMapper.toProject(request);

        //Assert
        assert actual.getName().equals(request.getName());
        assert actual.getDescription().equals(request.getDescription());
    }

    @Test
    void toProjectResponse(){
        var request = new Project();
        request.setName("test");
        request.setDescription("test");

        var actual = ProjectMapper.toProjectResponse(request);

        assert actual.getName().equals(request.getName());
        assert actual.getDescription().equals(request.getDescription());
    }
}
