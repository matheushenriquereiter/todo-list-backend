package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.exceptions.UserNotFoundException;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public User getUserFromToken(String jwtToken) {
        String userEmail = jwtService.extractUsername(jwtToken);

        return userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
    }
}
