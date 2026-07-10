package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.TagRequestDTO;
import io.github.matheushenriquereiter.project.dtos.TagResponseDTO;
import io.github.matheushenriquereiter.project.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tags")
@CrossOrigin(origins = "http://localhost:5173")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagResponseDTO> createTag(@RequestBody TagRequestDTO tagRequestDTO) {
        TagResponseDTO tagResponseDTO = tagService.createTag(tagRequestDTO);

        return ResponseEntity.ok(tagResponseDTO);
    }
}
