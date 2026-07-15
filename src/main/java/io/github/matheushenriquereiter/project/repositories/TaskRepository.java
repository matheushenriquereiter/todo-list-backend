package io.github.matheushenriquereiter.project.repositories;

import io.github.matheushenriquereiter.project.models.TaskEntity;
import io.github.matheushenriquereiter.project.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByUser(UserEntity userEntity);
    Page<TaskEntity> findAllByUser(UserEntity userEntity, Pageable pageable);
}
