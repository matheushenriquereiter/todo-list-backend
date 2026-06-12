package io.github.matheushenriquereiter.project.controller;

import io.github.matheushenriquereiter.project.dto.TaskDTO;
import io.github.matheushenriquereiter.project.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    public TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody TaskDTO taskDTO) {
        taskService.create(taskDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("userId") Long userId) {
        taskService.delete(userId);

        return ResponseEntity.ok().build();
    }
}
