package io.github.matheushenriquereiter.project.dtos;

import io.github.matheushenriquereiter.project.enums.TaskStatus;

public record TaskStatusDTO(
        TaskStatus status
) {
}
