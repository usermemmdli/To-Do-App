package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dto.request.UserEditRequest;
import com.example.Task_Management_App.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersMapperTest {
    @Autowired
    private UsersMapper UsersMapper;

    @Test
    void toUser() {
        //Arrange
        var request = new UserEditRequest();
        request.setUsername("test");
        request.setEmail("test@test");

        var expected = UserResponse.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();

        //Actual
        var actual = UsersMapper.toUsers(request);

        //Assert
        assert actual.getUsername().equals(expected.getUsername());
        assert actual.getEmail().equals(expected.getEmail());
    }

    @Test
    void toUsersResponse() {
        var request = new Users();
        request.setUsername("test");
        request.setEmail("test@test");

        var expected = UserResponse.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();

        var actual = UsersMapper.toUsersResponse(request);

        assert actual.getUsername().equals(expected.getUsername());
        assert actual.getEmail().equals(expected.getEmail());
    }
}
