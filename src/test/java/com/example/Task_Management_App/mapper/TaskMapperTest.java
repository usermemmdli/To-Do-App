package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.dto.request.TaskRequest;
import com.example.Task_Management_App.dto.response.TaskResponse;
import com.example.Task_Management_App.enums.Priority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class TaskMapperTest {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    void toTask() {
        //Arrange
        var request = new TaskRequest();
        request.setTitle("test");
        request.setDescription("test");
        request.setCompleted(false);

        var expected = TaskResponse.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.getCompleted())
                .build();

        //Actual
        var actual = taskMapper.toTask(request);

        //Assert
        assert actual.getTitle().equals(expected.getTitle());
        assert actual.getDescription().equals(expected.getDescription());
        assert !actual.getCompleted();
    }

    @Test
    void toTaskResponse() {
        var request = new Task();
        request.setTitle("test");
        request.setDescription("test");
        request.setCompleted(false);
        request.setPriority(Priority.High);

        var expected = TaskResponse.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.getCompleted())
                .priority(String.valueOf(request.getPriority()))
                .build();

        var actual = taskMapper.toTaskResponse(request);

        assert actual.getTitle().equals(expected.getTitle());
        assert actual.getDescription().equals(expected.getDescription());
        assert actual.getCompleted().equals(expected.getCompleted());
        assertSame(Priority.High, request.getPriority());
    }
}
