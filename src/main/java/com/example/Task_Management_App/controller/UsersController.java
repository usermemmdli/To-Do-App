package com.example.Task_Management_App.controller;

import com.example.Task_Management_App.dto.request.UserEditRequest;
import com.example.Task_Management_App.dto.response.UsersResponse;
import com.example.Task_Management_App.security.AuthenticatedHelperService;
import com.example.Task_Management_App.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    private final AuthenticatedHelperService authenticatedHelperService;

    @PutMapping("/edit-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UsersResponse> editUser(@RequestBody UserEditRequest userEditRequest) {
        String currentUserEmail = authenticatedHelperService.getCurrentUserEmail();
        UsersResponse userResponse = usersService.editUser(currentUserEmail, userEditRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

}
