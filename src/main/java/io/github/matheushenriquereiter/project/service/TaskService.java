package io.github.matheushenriquereiter.project.service;

import io.github.matheushenriquereiter.project.dto.TaskDTO;
import io.github.matheushenriquereiter.project.exception.InvalidAttributeException;
import io.github.matheushenriquereiter.project.model.Task;
import io.github.matheushenriquereiter.project.model.User;
import io.github.matheushenriquereiter.project.repository.TaskRepository;
import io.github.matheushenriquereiter.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findById(taskDTO.getUserId()).orElseThrow(() -> new InvalidAttributeException("userId", "No user is associated with this ID"));

        Task task = new Task(taskDTO.getTitle(), taskDTO.getDescription(), user);

        taskRepository.save(task);
    }
}
