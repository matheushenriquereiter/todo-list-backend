package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.TagRequestDTO;
import io.github.matheushenriquereiter.project.dtos.TagResponseDTO;
import io.github.matheushenriquereiter.project.exceptions.TagAlreadyExists;
import io.github.matheushenriquereiter.project.models.Tag;
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

    public TagResponseDTO createTag(TagRequestDTO tagRequestDTO) {
        Optional<Tag> tag = tagRepository.findTagByName(tagRequestDTO.name());

        if (tag.isPresent()) {
            throw new TagAlreadyExists();
        }

        Tag createdTag = tagRepository.save(new Tag(tagRequestDTO.name()));

        return new TagResponseDTO(createdTag.getId(), createdTag.getName());
    }
}
