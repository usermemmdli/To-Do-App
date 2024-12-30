package com.example.Task_Management_App.dao.repository;

import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.enums.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByPriority(Priority priority);

    Optional<Task> findByCompleted(Boolean completed);

    Optional<Object> findByTitle(String title);
}
