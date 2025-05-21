package com.example.Task_Management_App.exception;

public class TaskCannotDeletedException extends RuntimeException {
    public TaskCannotDeletedException(String message) {
        super(message);
    }
}
