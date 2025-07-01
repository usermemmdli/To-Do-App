package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dto.request.ProjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectMapperTest {
    @Autowired
    private ProjectMapper projectMapper;

    @Test
    void toProject() {
        //Arrange
        var request = new ProjectRequest();
        request.setName("test");
        request.setDescription("test");

        //Actual
        var actual = request;

        //Assert
        assert actual.getName().equals(request.getName());
        assert actual.getDescription().equals(request.getDescription());
    }

    @Test
    void toProjectResponse() {
        var request = new Project();
        request.setName("test");
        request.setDescription("test");

        var actual = projectMapper.toProjectResponse(request);

        assert actual.getName().equals(request.getName());
        assert actual.getDescription().equals(request.getDescription());
    }
}
