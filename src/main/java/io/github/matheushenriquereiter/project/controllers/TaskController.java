package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.CreateTaskDTO;
import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "http://localhost:5173/")
public class TaskController {
    public final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Serializable> create(@CookieValue("jwtToken") String jwtToken, @Valid @RequestBody CreateTaskDTO createTaskDTO) {
        taskService.create(jwtToken, createTaskDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Serializable> delete(@CookieValue("jwtToken") String jwtToken, @PathVariable("taskId") Long taskId) {
        taskService.delete(jwtToken, taskId);

        return ResponseEntity.ok().build();
    }
}
