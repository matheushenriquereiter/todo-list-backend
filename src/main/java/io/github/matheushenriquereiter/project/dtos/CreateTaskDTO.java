package io.github.matheushenriquereiter.project.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDTO {
    @NotNull(message = "Task title cannot be empty")
    @Size(min = 3, max = 50, message = "Task title must be between 3 and 50 characters long")
    private String title;

    @NotNull(message = "Task description cannot be empty")
    @Size(min = 3, max = 50, message = "Task description must be between 3 and 50 characters long")
    private String description;
}
