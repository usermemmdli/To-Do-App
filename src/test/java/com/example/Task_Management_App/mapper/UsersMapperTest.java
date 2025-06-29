package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dto.request.UserEditRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class UsersMapperTest {
    @Autowired
    private UsersMapper UsersMapper;

    @Test
    void toUser() {
        //Arrange
        var request = new UserEditRequest();
        request.setUsername("test");
        request.setEmail("test@test");

        //Actual
        var actual = UsersMapper.toUsers(request);

        //Assert
        assert actual.getUsername().equals(request.getUsername());
        assert actual.getEmail().equals(request.getEmail());
    }

    @Test
    void toUsersResponse() {
        var request = new Users();
        request.setUsername("test");
        request.setEmail("test@test");

        var actual = UsersMapper.toUsersResponse(request);

        assert actual.getUsername().equals(request.getUsername());
        assert actual.getEmail().equals(request.getEmail());
    }
}
