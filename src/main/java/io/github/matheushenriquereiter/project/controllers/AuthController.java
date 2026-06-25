package io.github.matheushenriquereiter.project.controllers;

import io.github.matheushenriquereiter.project.dtos.JwtTokenDTO;
import io.github.matheushenriquereiter.project.dtos.UserDTO;
import io.github.matheushenriquereiter.project.dtos.UserLoginDTO;
import io.github.matheushenriquereiter.project.dtos.UserRegisterDTO;
import io.github.matheushenriquereiter.project.exceptions.UserNotFoundException;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.UserRepository;
import io.github.matheushenriquereiter.project.services.AuthService;
import io.github.matheushenriquereiter.project.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {
    public final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, JwtService jwtService, UserRepository userRepository) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
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
        String userEmail = jwtService.extractUsername(jwtToken);
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        UserDTO userDTO = new UserDTO(user.getUsername(), user.getEmail());

        return ResponseEntity.ok(userDTO);
    }
}
