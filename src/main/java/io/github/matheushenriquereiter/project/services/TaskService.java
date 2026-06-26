package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.CreateTaskDTO;
import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.exceptions.UserNotFoundException;
import io.github.matheushenriquereiter.project.models.Task;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public void create(String jwtToken, CreateTaskDTO createTaskDTO) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = new Task(createTaskDTO.getTitle(), createTaskDTO.getDescription(), user);

        taskRepository.save(task);
    }
}
