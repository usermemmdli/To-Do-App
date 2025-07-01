package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dto.response.ProjectResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectMapperTest {
    @Autowired
    private ProjectMapper projectMapper;

    @Test
    void toProjectResponse() {
        var request = new Project();
        request.setName("test");
        request.setDescription("test");

        var expected = ProjectResponse.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        var actual = projectMapper.toProjectResponse(request);

        assert actual.getName().equals(expected.getName());
        assert actual.getDescription().equals(expected.getDescription());
    }
}
