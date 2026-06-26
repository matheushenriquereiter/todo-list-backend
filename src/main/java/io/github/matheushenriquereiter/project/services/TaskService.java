package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.TaskRequestDTO;
import io.github.matheushenriquereiter.project.exceptions.TaskNotFoundException;
import io.github.matheushenriquereiter.project.models.Task;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.TaskRepository;
import io.github.matheushenriquereiter.project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public void create(String jwtToken, TaskRequestDTO taskRequestDTO) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = new Task(taskRequestDTO.getTitle(), taskRequestDTO.getDescription(), user);

        taskRepository.save(task);
    }

    public void delete(String jwtToken, Long taskId) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        if (!Objects.equals(user.getId(), task.getId())) {
            throw new TaskNotFoundException();
        }

        taskRepository.delete(task);
    }

    public void update(String jwtToken, Long taskId, TaskRequestDTO taskRequestDTO) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        if (!user.getId().equals(task.getId())) {
            throw new TaskNotFoundException();
        }

        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());

        taskRepository.save(task);
    }
}
