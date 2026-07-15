package io.github.matheushenriquereiter.project.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record TaskRequestDTO(
        @NotNull(message = "Task title cannot be null")
        @Size(min = 3, max = 50, message = "Task title must be between 3 and 50 characters long")
        String title,

        @NotNull(message = "Task description cannot be null")
        @Size(min = 3, max = 50, message = "Task description must be between 3 and 50 characters long")
        String description
) {
}
