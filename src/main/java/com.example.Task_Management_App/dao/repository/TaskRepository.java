package com.example.Task_Management_App.dao.repository;

import com.example.Task_Management_App.dao.entity.Task;
import com.example.Task_Management_App.enums.Priority;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @EntityGraph(attributePaths = {"project"})
    Optional<Task> findByPriority(Priority priority);

    @EntityGraph(attributePaths = {"project"})
    Optional<Task> findByCompleted(Boolean completed);

    @EntityGraph(attributePaths = {"project"})
    Optional<Object> findByTitle(String title);
}
