package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.JwtTokenDTO;
import io.github.matheushenriquereiter.project.dtos.UserRegisterDTO;
import io.github.matheushenriquereiter.project.dtos.UserLoginDTO;
import io.github.matheushenriquereiter.project.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
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
}
