package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskDTO>> getUserTasks(@PathVariable Long userId) {
        List<TaskDTO> userTasks = userService.getTasks(userId);

        return ResponseEntity.ok(userTasks);
    }
}
