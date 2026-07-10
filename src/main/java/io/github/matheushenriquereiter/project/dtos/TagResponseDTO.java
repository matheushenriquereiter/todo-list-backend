package io.github.matheushenriquereiter.project.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TagResponseDTO(
        @NotNull(message = "Tag ID cannot be null")
        Long id,

        @NotNull(message = "Tag name cannot be null")
        @Size(min = 3, max = 20, message = "Tag name must be between 3 and 20 characters long")
        String name
) {
}
