package io.github.matheushenriquereiter.project.repositories;

import io.github.matheushenriquereiter.project.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
