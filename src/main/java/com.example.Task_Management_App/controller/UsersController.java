package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.UserChangePasswordRequest;
import com.example.Task_Management_App.dto.request.UserDeleteRequest;
import com.example.Task_Management_App.dto.request.UserEditRequest;
import com.example.Task_Management_App.dto.response.UserResponse;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import com.example.Task_Management_App.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    private final AuthenticatedHelperService authenticatedHelperService;

    @PutMapping("/edit-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> editUser(@RequestBody UserEditRequest userEditRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        UserResponse userResponse = usersService.editUser(currentUserEmail, userEditRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PatchMapping("/edit-user/change-password")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        usersService.changePassword(currentUserEmail, userChangePasswordRequest);
    }

    @DeleteMapping("/delete-user")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestBody UserDeleteRequest userDeleteRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        usersService.deleteUser(currentUserEmail, userDeleteRequest);
    }
}
