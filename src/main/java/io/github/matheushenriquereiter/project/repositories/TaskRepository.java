package io.github.matheushenriquereiter.project.repositories;

import io.github.matheushenriquereiter.project.models.Task;
import io.github.matheushenriquereiter.project.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser(User user);
    Page<Task> findAllByUser(User user, Pageable pageable);
}
