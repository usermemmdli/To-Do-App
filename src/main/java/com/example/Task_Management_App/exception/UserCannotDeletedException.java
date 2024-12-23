package com.example.Task_Management_App.exception;

public class UserCannotDeletedException extends RuntimeException {
    public UserCannotDeletedException(String message) {
        super(message);
    }
}
