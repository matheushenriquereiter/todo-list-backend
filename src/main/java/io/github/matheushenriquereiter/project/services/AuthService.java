package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.JwtTokenDTO;
import io.github.matheushenriquereiter.project.dtos.UserLoginDTO;
import io.github.matheushenriquereiter.project.dtos.UserRegisterDTO;
import io.github.matheushenriquereiter.project.exceptions.EmailAlreadyTakenException;
import io.github.matheushenriquereiter.project.exceptions.InvalidCredentialsException;
import io.github.matheushenriquereiter.project.models.UserEntity;
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
        Optional<UserEntity> user = userRepository.findByEmail(userRegisterDTO.email());

        if (user.isPresent()) {
            throw new EmailAlreadyTakenException();
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.password());

        userRepository.save(new UserEntity(userRegisterDTO.username(), userRegisterDTO.email(), encodedPassword));
    }

    public JwtTokenDTO login(UserLoginDTO userLoginDTO) {
        UserEntity userEntity = userRepository.findByEmail(userLoginDTO.email()).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(userLoginDTO.password(), userEntity.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return new JwtTokenDTO(jwtService.generateToken(userLoginDTO.email()));
    }
}
