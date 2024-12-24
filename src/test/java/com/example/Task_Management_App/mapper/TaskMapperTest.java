package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.dto.request.TaskRequest;
import com.example.Task_Management_App.enums.Priority;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TaskMapperTest {
    @Test
    void toTask() {
        //Arrange
        var request = new TaskRequest();
        request.setTitle("test");
        request.setDescription("test");
        request.setCompleted(false);

        //Actual
        var actual = TaskMapper.toTask(request);

        //Assert
        assert actual.getTitle().equals("test");
        assert actual.getDescription().equals("test");
        assert !actual.getCompleted();
    }

    @Test
    void toTaskResponse() {
        var request = new Task();
        request.setTitle("test");
        request.setDescription("test");
        request.setCompleted(false);
        request.setPriority(Priority.High);

        var actual = TaskMapper.toTaskResponse(request);

        assert actual.getTitle().equals("test");
        assert actual.getDescription().equals("test");
        assert !actual.getCompleted();
        assertSame(Priority.High, request.getPriority());
    }
}
