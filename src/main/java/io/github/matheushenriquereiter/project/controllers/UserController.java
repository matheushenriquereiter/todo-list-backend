package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/tasks")
    public ResponseEntity<List<TaskDTO>> getUserTasks(@CookieValue("jwtToken") String jwtToken) {
        List<TaskDTO> userTasks = userService.getUserTasks(jwtToken);

        return ResponseEntity.ok(userTasks);
    }
}
