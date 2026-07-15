package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.TagRequestDTO;
import io.github.matheushenriquereiter.project.dtos.TagResponseDTO;
import io.github.matheushenriquereiter.project.exceptions.EntityNotFoundException;
import io.github.matheushenriquereiter.project.exceptions.EntityAlreadyExistsException;
import io.github.matheushenriquereiter.project.models.TagEntity;
import io.github.matheushenriquereiter.project.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagResponseDTO getTagById(Long tagId) {
        TagEntity tagEntity = tagRepository.findById(tagId).orElseThrow(() -> new EntityNotFoundException("Tag not found"));

        return new TagResponseDTO(tagEntity.getId(), tagEntity.getName());
    }

    public TagResponseDTO createTag(TagRequestDTO tagRequestDTO) {
        Optional<TagEntity> tag = tagRepository.findTagByName(tagRequestDTO.name());

        if (tag.isPresent()) {
            throw new EntityAlreadyExistsException("Tag already exists");
        }

        TagEntity createdTagEntity = tagRepository.save(new TagEntity(tagRequestDTO.name()));

        return new TagResponseDTO(createdTagEntity.getId(), createdTagEntity.getName());
    }
}
