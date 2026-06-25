package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.exceptions.UserNotFoundException;
import io.github.matheushenriquereiter.project.models.Task;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public List<TaskDTO> getTasks(String jwtToken) {
        User user = getUserFromToken(jwtToken);
        Set<Task> tasks = user.getTasks();

        return tasks.stream().map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getUser().getId())).toList();
    }
}
