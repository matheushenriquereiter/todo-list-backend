package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.exceptions.InvalidCredentialsException;
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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<TaskDTO> getTasks(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(InvalidCredentialsException::new);

        Set<Task> tasks = user.getTasks();

        return tasks.stream().map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getUser().getId())).toList();
    }
}
