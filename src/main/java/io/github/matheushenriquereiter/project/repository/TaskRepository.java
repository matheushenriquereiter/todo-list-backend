package io.github.matheushenriquereiter.project.repository;

import io.github.matheushenriquereiter.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
