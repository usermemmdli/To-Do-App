package com.example.Task_Management_App.dao.repository;

import com.example.Task_Management_App.dao.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @EntityGraph(attributePaths = {"users"})
    Optional<Role> findByName(String user);
}
