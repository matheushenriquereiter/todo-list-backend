package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.TaskRequestDTO;
import io.github.matheushenriquereiter.project.dtos.TaskResponseDTO;
import io.github.matheushenriquereiter.project.dtos.TaskStatusDTO;
import io.github.matheushenriquereiter.project.enums.TaskStatus;
import io.github.matheushenriquereiter.project.exceptions.EntityNotFoundException;
import io.github.matheushenriquereiter.project.models.Task;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.Comparator;
import java.util.List;
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

    public List<TaskResponseDTO> getTasks(String jwtToken) {
        User user = userService.getUserFromToken(jwtToken);

        return taskRepository.findAllByUser(user)
                .stream()
                .sorted(Comparator.comparing(Task::getCreatedAt))
                .map(Task::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getPaginatedTasks(String jwtToken, int limit, int offset) {
        User user = userService.getUserFromToken(jwtToken);
        Pageable pagination = PageRequest.of(offset, limit);

        return taskRepository.findAllByUser(user, pagination)
                .stream()
                .map(Task::toResponseDTO)
                .toList();
    }

    public void createTask(String jwtToken, TaskRequestDTO taskRequestDTO) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = new Task(taskRequestDTO.getTitle(), taskRequestDTO.getDescription(), TaskStatus.TO_DO, user);

        taskRepository.save(task);
    }

    public void deleteTask(String jwtToken, Long taskId) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);

        if (!Objects.equals(user.getId(), task.getUser().getId())) {
            throw new EntityNotFoundException();
        }

        taskRepository.delete(task);
    }

    public void updateTask(String jwtToken, Long taskId, TaskRequestDTO taskRequestDTO) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);

        if (!user.getId().equals(task.getId())) {
            throw new EntityNotFoundException();
        }

        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());

        taskRepository.save(task);
    }

    public void updateTaskStatus(String jwtToken, Long taskId, TaskStatusDTO taskStatusDTO) {
        User user = userService.getUserFromToken(jwtToken);
        Task task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);

        if (!user.getId().equals(task.getId())) {
            throw new EntityNotFoundException();
        }

        task.setStatus(taskStatusDTO.status());
        taskRepository.save(task);
    }
}
