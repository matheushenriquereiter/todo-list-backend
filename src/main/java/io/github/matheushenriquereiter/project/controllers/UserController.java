package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.JwtTokenDTO;
import io.github.matheushenriquereiter.project.dtos.UserRegisterDTO;
import io.github.matheushenriquereiter.project.dtos.UserLoginDTO;
import io.github.matheushenriquereiter.project.models.Task;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        JwtTokenDTO jwtTokenDTO = userService.login(userLoginDTO);

        return ResponseEntity.ok(jwtTokenDTO);
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<Set<Task>> getUserTasks(@PathVariable Long userId) {
        Set<Task> userTasks = userService.getTasks(userId);

        return ResponseEntity.ok(userTasks);
    }
}
