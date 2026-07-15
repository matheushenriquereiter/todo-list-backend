package io.github.matheushenriquereiter.project.models;

import io.github.matheushenriquereiter.project.dtos.TagResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tags")
@NoArgsConstructor
@Getter
@Setter
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    Set<TaskEntity> tasks;

    public TagEntity(String name) {
        this.name = name;
    }

    public TagResponseDTO toDTO() {
        return new TagResponseDTO(id, name);
    }
}
