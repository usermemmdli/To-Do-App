# Task-Management-Project

This project is a task management application that provides various functionalities for managing users, projects, tasks, and user authentication. 
It integrates **Spring Security** for authentication and authorization, and uses **JWT (JSON Web Tokens)** for secure token-based user authentication.

## Project Objectives

The main goal of this project is to implement various services for task management operations, including:
* User registration and login (with JWT)
* Managing user profiles
* Handling project-related operations
* Managing tasks

## Features

**AuthService**

Handles authentication and user management operations:
* **registerUser**: Allows new users to register by providing their details
* **loginUser**: Handles user login, validates credentials, and returns a JWT token

**UserService**

Handles all operations related to managing user profiles:
* **editProfile**: Allows users to update their profile information
* **changePassword**: Enables users to update their password
* **deleteUser**: Permits users to delete their account

**ProjectService**

Handles all operations related to managing projects:
* **createProject**: Creates a new project with specified details
* **editProject**: Allows users to update project information
* **deleteProject**: Permits project deletion by authorized users

**TaskService**

Handles all operations related to tasks:
* **createTask**: Creates new tasks within projects
* **editTask**: Allows updating task details
* **deleteTask**: Permits task deletion
* **setComplete**: Marks tasks as completed
* **setPriority**: Sets task priority levels

## Technologies Used

* **Java 17**: Programming language for building the application
* **Spring Boot**: Backend framework for creating RESTful APIs
* **Spring Security**: Provides authentication and authorization mechanisms, ensuring secure access
* **JWT (JSON Web Token)**: Used for secure, token-based authentication
* **Spring Data JPA**: Simplifies data access and integrates seamlessly with the database layer
* **PostgreSQL**: Database for storing user, project, and task data
* **JPA (Java Persistence API)**: For object-relational mapping and database interaction
