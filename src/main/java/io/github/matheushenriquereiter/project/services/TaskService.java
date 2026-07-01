package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.TaskRequestDTO;
import io.github.matheushenriquereiter.project.dtos.TaskResponseDTO;
import io.github.matheushenriquereiter.project.exceptions.TaskNotFoundException;
import io.github.matheushenriquereiter.project.models.Task;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public List<TaskResponseDTO> getTasks(String jwtToken) {
        User user = userService.getUserFromToken(jwtToken);
        Set<Task> tasks = user.getTasks();

        return tasks.stream()
                .sorted(Comparator.comparing(Task::getCreatedAt))
                .map(task -> new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getCreatedAt())).toList();
    }

    public void createTask(String jwtToken, TaskRequestDTO taskRequestDTO) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = new Task(taskRequestDTO.getTitle(), taskRequestDTO.getDescription(), user);

        taskRepository.save(task);
    }

    public void deleteTask(String jwtToken, Long taskId) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        if (!Objects.equals(user.getId(), task.getId())) {
            throw new TaskNotFoundException();
        }

        taskRepository.delete(task);
    }

    public void updateTask(String jwtToken, Long taskId, TaskRequestDTO taskRequestDTO) {
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
