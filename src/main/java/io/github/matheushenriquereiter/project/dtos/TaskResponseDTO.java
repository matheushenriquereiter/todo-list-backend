package io.github.matheushenriquereiter.project.dtos;

import io.github.matheushenriquereiter.project.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record TaskResponseDTO(
        @NotNull(message = "Task ID cannot be null")
        Long id,

        @NotNull(message = "Task title cannot be null")
        @Size(min = 3, max = 50, message = "Task title must be between 3 and 50 characters long")
        String title,

        @NotNull(message = "Task description cannot be null")
        @Size(min = 3, max = 50, message = "Task description must be between 3 and 50 characters long")
        String description,

        @NotNull(message = "Task status cannot be null")
        TaskStatus status,

        @NotNull(message = "Task createdAt cannot be null")
        Instant createdAt
) {
}
