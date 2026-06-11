package io.github.matheushenriquereiter.project.controller;

import io.github.matheushenriquereiter.project.dto.JwtTokenDTO;
import io.github.matheushenriquereiter.project.dto.UserRegisterDTO;
import io.github.matheushenriquereiter.project.dto.UserLoginDTO;
import io.github.matheushenriquereiter.project.service.UserService;
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
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        JwtTokenDTO jwtTokenDTO = userService.login(userLoginDTO);

        return ResponseEntity.ok(jwtTokenDTO);
    }
}
