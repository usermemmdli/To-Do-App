package com.example.Task_Management_App.service;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.dto.request.UserChangePasswordRequest;
import com.example.Task_Management_App.dto.request.UserEditRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class UsersServiceTest {
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;

    @Test
    void testEditUser() {
        Users user = new Users();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test");
        usersRepository.save(user);

        UserEditRequest userEditRequest = new UserEditRequest();
        userEditRequest.setUsername("test request");
        userEditRequest.setEmail("test request");

        user.setUsername(userEditRequest.getUsername());
        user.setEmail(userEditRequest.getEmail());
        usersRepository.save(user);

        assertTrue(usersRepository.findByEmail(userEditRequest.getEmail()).isPresent());
    }

    @Test
    void testChangePassword() {
        Users user = new Users();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test");
        usersRepository.save(user);

        UserChangePasswordRequest userChangePasswordRequest = new UserChangePasswordRequest();
        userChangePasswordRequest.setOldPassword(user.getPassword());
        userChangePasswordRequest.setNewPassword("test password");

        Users existingUser = usersRepository.findByUsername(user.getUsername());
        assertThat(existingUser).isNotNull();
        assertThat(existingUser.getPassword()).isEqualTo(userChangePasswordRequest.getOldPassword());

        existingUser.setPassword(userChangePasswordRequest.getNewPassword());
        usersRepository.save(existingUser);

        Users updatedUser = usersRepository.findById(existingUser.getId()).orElse(null);
        assertThat(updatedUser).isNotNull();
    }

    @Test
    void testDeleteUser() {
        Users user = new Users();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test");
        usersRepository.save(user);

        usersService.deleteUser(user.getEmail(), user.getId());
    }
}
