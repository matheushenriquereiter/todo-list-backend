package io.github.matheushenriquereiter.project.repositories;

import io.github.matheushenriquereiter.project.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(Long id);
}
