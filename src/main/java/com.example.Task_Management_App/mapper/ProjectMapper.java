package com.example.Task_Management_App.mapper;

import com.example.Task_Management_App.dao.entity.Project;
import com.example.Task_Management_App.dto.response.ProjectResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
@Mapper(componentModel = "spring")
public class ProjectMapper {
    public ProjectResponse toProjectResponse(Project project) {
        return ProjectResponse.builder()
                .name(project.getName())
                .description(project.getDescription())
                .updatedAt(Timestamp.from(Instant.now()))
                .createdAt(Timestamp.from(Instant.now()))
                .build();
    }
}
