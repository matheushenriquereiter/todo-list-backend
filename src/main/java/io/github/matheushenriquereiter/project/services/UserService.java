package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.exceptions.EntityNotFoundException;
import io.github.matheushenriquereiter.project.models.UserEntity;
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

    public UserEntity getUserFromToken(String jwtToken) {
        String userEmail = jwtService.extractUsername(jwtToken);

        return userRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
