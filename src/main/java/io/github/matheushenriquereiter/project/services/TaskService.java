package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.TaskDTO;
import io.github.matheushenriquereiter.project.exceptions.UserNotFoundException;
import io.github.matheushenriquereiter.project.models.Task;
import io.github.matheushenriquereiter.project.models.User;
import io.github.matheushenriquereiter.project.repositories.TaskRepository;
import io.github.matheushenriquereiter.project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void create(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId()).orElseThrow(UserNotFoundException::new);

        Task task = new Task(taskDTO.getTitle(), taskDTO.getDescription(), user);

        taskRepository.save(task);
    }

    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public void update(TaskDTO taskDTO, Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        Task task = taskOptional.get();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());

        taskRepository.save(task);
    }
}
