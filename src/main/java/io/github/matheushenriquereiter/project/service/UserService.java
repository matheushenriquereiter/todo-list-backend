package io.github.matheushenriquereiter.project.service;

import io.github.matheushenriquereiter.project.dto.JwtTokenDTO;
import io.github.matheushenriquereiter.project.dto.UserRegisterDTO;
import io.github.matheushenriquereiter.project.dto.UserLoginDTO;
import io.github.matheushenriquereiter.project.exception.DuplicateEmailException;
import io.github.matheushenriquereiter.project.exception.InvalidCredentialException;
import io.github.matheushenriquereiter.project.model.User;
import io.github.matheushenriquereiter.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO == null) {
            throw new IllegalArgumentException("UserDTO can't be null");
        }

        Optional<User> user = userRepository.findByEmail(userRegisterDTO.getEmail());

        if (user.isPresent()) {
            throw new DuplicateEmailException();
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());

        userRepository.save(new User(userRegisterDTO.getUsername(), userRegisterDTO.getEmail(), encodedPassword));
    }

    public JwtTokenDTO login(UserLoginDTO userLoginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userLoginDTO.getEmail());

        if (userOptional.isEmpty()) {
            throw new InvalidCredentialException("email", "Email not associated with a user");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("password", "Incorrect password");
        }

        return new JwtTokenDTO(jwtService.generateToken(userLoginDTO.getEmail()));
    }
}
