package com.example.Task_Management_App.dao.repository;

import com.example.Task_Management_App.dao.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @EntityGraph(attributePaths = {"tasks"})
    Optional<Project> findByIdAndUsersId(Long projectId, Long userId);
}
