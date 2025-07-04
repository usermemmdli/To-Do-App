package com.example.Task_Management_App.dao.repository;

import com.example.Task_Management_App.dao.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @EntityGraph(attributePaths = {"roles","projects"})
    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

    Users findByUsername(String username);
}
