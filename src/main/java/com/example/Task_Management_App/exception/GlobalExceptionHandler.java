package com.example.Task_Management_App.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<?> handleInvalidEmailException(InvalidEmailException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Email is already taken");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Default role not found");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEmailOrPasswordException.class)
    public ResponseEntity<?> handleInvalidEmailOrPasswordException(InvalidEmailOrPasswordException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Email or password is invalid");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<?> handleProjectNotFoundException(ProjectNotFoundException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Project not found");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectCannotDeletedException.class)
    public ResponseEntity<?> handleProjectCannotDeletedException(ProjectCannotDeletedException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Project cannot be deleted! Project does not belong to the user");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFoundException(TaskNotFoundException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Task not found or does not belong to the user");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskCannotDeletedException.class)
    public ResponseEntity<?> handleTaskCannotDeletedException(TaskCannotDeletedException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Task cannot be deleted! Task does not belong to the user");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserCannotDeletedException.class)
    public ResponseEntity<?> handleUserCannotDeletedException(UserCannotDeletedException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "User cannot be deleted! User does not belong to the user");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "User not found");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Password does not match");
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
