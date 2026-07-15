package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.TaskRequestDTO;
import io.github.matheushenriquereiter.project.dtos.TaskResponseDTO;
import io.github.matheushenriquereiter.project.dtos.TaskStatusDTO;
import io.github.matheushenriquereiter.project.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class TaskController {
    public final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(@CookieValue("jwtToken") String jwtToken) {
        return ResponseEntity.ok(taskService.getTasks(jwtToken));
    }

    @GetMapping(value = "/tasks", params = {"limit", "offset"})
    public ResponseEntity<List<TaskResponseDTO>> getPaginatedTasks(@CookieValue("jwtToken") String jwtToken, @RequestParam int limit, @RequestParam int offset) {
        return ResponseEntity.ok(taskService.getPaginatedTasks(jwtToken, limit, offset));
    }

    @PostMapping("/tasks")
    public ResponseEntity<Serializable> create(@CookieValue("jwtToken") String jwtToken, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.createTask(jwtToken, taskRequestDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Serializable> delete(@CookieValue("jwtToken") String jwtToken, @PathVariable Long taskId) {
        taskService.deleteTask(jwtToken, taskId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<Serializable> update(@CookieValue("jwtToken") String jwtToken, @PathVariable Long taskId, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.updateTask(jwtToken, taskId, taskRequestDTO);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/tasks/{taskId}/status")
    public ResponseEntity<Serializable> updateTaskStatus(@CookieValue("jwtToken") String jwtToken, @PathVariable Long taskId, @Valid @RequestBody TaskStatusDTO taskStatusDTO) {
        taskService.updateTaskStatus(jwtToken, taskId, taskStatusDTO);

        return ResponseEntity.ok().build();
    }
}
