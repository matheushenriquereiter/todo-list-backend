package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.JwtTokenDTO;
import io.github.matheushenriquereiter.project.dtos.UserDTO;
import io.github.matheushenriquereiter.project.dtos.UserLoginDTO;
import io.github.matheushenriquereiter.project.dtos.UserRegisterDTO;
import io.github.matheushenriquereiter.project.models.UserEntity;
import io.github.matheushenriquereiter.project.services.AuthService;
import io.github.matheushenriquereiter.project.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    public final AuthService authService;
    public final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Serializable> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        authService.register(userRegisterDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        JwtTokenDTO jwtTokenDTO = authService.login(userLoginDTO);

        return ResponseEntity.ok(jwtTokenDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(@CookieValue("jwtToken") String jwtToken) {
        UserEntity userEntity = userService.getUserFromToken(jwtToken);
        UserDTO userDTO = new UserDTO(userEntity.getUsername(), userEntity.getEmail());

        return ResponseEntity.ok(userDTO);
    }
}
