package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.services.TaskService;
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
    public ResponseEntity<?> delete(@RequestParam("taskId") Long taskId) {
        taskService.delete(taskId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody TaskDTO taskDTO, Long taskId) {
        taskService.update(taskDTO, taskId);

        return ResponseEntity.ok().build();
    }
}
