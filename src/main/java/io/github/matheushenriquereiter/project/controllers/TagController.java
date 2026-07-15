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

    @GetMapping("/{tagId}")
    public ResponseEntity<TagResponseDTO> getTag(@PathVariable Long tagId) {
        return ResponseEntity.ok(tagService.getTagById(tagId));
    }

    @PostMapping
    public ResponseEntity<TagResponseDTO> createTag(@RequestBody TagRequestDTO tagRequestDTO) {
        return ResponseEntity.ok(tagService.createTag(tagRequestDTO));
    }
}
