package com.example.Task_Management_App.dao.entity;

import com.example.Task_Management_App.enums.Priority;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Entity
@Builder
@Table(name = "task")
@Enabled
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    Boolean completed;
    @Enumerated(EnumType.STRING)
    Priority priority;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    Project project;
    Timestamp createdAt;
    Timestamp updatedAt;
}
