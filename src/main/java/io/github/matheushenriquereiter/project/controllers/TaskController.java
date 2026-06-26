package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.CreateTaskDTO;
import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:5173/")
public class TaskController {
    public final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<Serializable> create(@CookieValue("jwtToken") String jwtToken, @Valid @RequestBody CreateTaskDTO createTaskDTO) {
        taskService.create(jwtToken, createTaskDTO);

        return ResponseEntity.ok().build();
    }
}
