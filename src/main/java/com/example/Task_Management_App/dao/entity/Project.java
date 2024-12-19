package com.example.Task_Management_App.dao.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Builder
@Table(name = "project")
@Enabled
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    Users users;
    Timestamp createdAt;
    Timestamp updatedAt;
    @OneToMany(mappedBy = "project",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    List<Task> tasks;
}
