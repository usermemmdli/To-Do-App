package com.example.Task_Management_App.exception;

public class ProjectCannotDeletedException extends RuntimeException {
    public ProjectCannotDeletedException(String message) {
        super(message);
    }
}
