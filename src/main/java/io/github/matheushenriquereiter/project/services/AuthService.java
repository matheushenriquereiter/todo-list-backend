package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.JwtTokenDTO;
import io.github.matheushenriquereiter.project.dtos.UserLoginDTO;
import io.github.matheushenriquereiter.project.dtos.UserRegisterDTO;
import io.github.matheushenriquereiter.project.exceptions.EmailAlreadyTakenException;
import io.github.matheushenriquereiter.project.exceptions.InvalidCredentialsException;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        Optional<User> user = userRepository.findByEmail(userRegisterDTO.getEmail());

        if (user.isPresent()) {
            throw new EmailAlreadyTakenException();
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());

        userRepository.save(new User(userRegisterDTO.getUsername(), userRegisterDTO.getEmail(), encodedPassword));
    }

    public JwtTokenDTO login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return new JwtTokenDTO(jwtService.generateToken(userLoginDTO.getEmail()));
    }
}
