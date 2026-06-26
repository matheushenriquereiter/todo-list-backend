package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.TaskRequestDTO;
import io.github.matheushenriquereiter.project.dtos.TaskResponseDTO;
import io.github.matheushenriquereiter.project.services.TaskService;
import io.github.matheushenriquereiter.project.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class TaskController {
    public final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getUserTasks(@CookieValue("jwtToken") String jwtToken) {
        List<TaskResponseDTO> userTasks = userService.getUserTasks(jwtToken);

        return ResponseEntity.ok(userTasks);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Serializable> create(@CookieValue("jwtToken") String jwtToken, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.create(jwtToken, taskRequestDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Serializable> delete(@CookieValue("jwtToken") String jwtToken, @PathVariable Long taskId) {
        taskService.delete(jwtToken, taskId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<Serializable> update(@CookieValue("jwtToken") String jwtToken,  @PathVariable Long taskId, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.update(jwtToken, taskId, taskRequestDTO);

        return ResponseEntity.ok().build();
    }
}
