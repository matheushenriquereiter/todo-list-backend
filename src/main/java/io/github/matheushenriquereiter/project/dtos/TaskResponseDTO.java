package io.github.matheushenriquereiter.project.dtos;

import io.github.matheushenriquereiter.project.enums.TaskStatus;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    @NotNull(message = "Task ID cannot be null")
    private Long id;

    @NotNull(message = "Task title cannot be null")
    @Size(min = 3, max = 50, message = "Task title must be between 3 and 50 characters long")
    private String title;

    @NotNull(message = "Task description cannot be null")
    @Size(min = 3, max = 50, message = "Task description must be between 3 and 50 characters long")
    private String description;

    @NotNull(message = "Task status cannot be null")
    private TaskStatus status;

    @NotNull(message = "Task createdAt cannot be null")
    private Instant createdAt;
}
