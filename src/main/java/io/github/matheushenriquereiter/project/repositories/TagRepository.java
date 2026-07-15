package io.github.matheushenriquereiter.project.repositories;

import io.github.matheushenriquereiter.project.models.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findTagByName(String name);
}
