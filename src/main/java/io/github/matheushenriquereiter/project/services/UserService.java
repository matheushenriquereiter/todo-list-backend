package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.JwtTokenDTO;
import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.dtos.UserLoginDTO;
import io.github.matheushenriquereiter.project.dtos.UserRegisterDTO;
import io.github.matheushenriquereiter.project.exceptions.EmailAlreadyTakenException;
import io.github.matheushenriquereiter.project.exceptions.InvalidCredentialsException;
import io.github.matheushenriquereiter.project.exceptions.UserNotFoundException;
import io.github.matheushenriquereiter.project.models.Task;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Optional<User> user = userRepository.findByEmail(userRegisterDTO.getEmail());

        if (user.isPresent()) {
            throw new EmailAlreadyTakenException();
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());

        userRepository.save(new User(userRegisterDTO.getUsername(), userRegisterDTO.getEmail(), encodedPassword));
    }

    public JwtTokenDTO login(UserLoginDTO userLoginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userLoginDTO.getEmail());

        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return new JwtTokenDTO(jwtService.generateToken(userLoginDTO.getEmail()));
    }

    public List<TaskDTO> getTasks(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();
        Set<Task> tasks = user.getTasks();

        return tasks.stream().map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getUser().getId())).toList();
    }
}
