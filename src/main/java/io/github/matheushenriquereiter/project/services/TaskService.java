package io.github.matheushenriquereiter.project.services;

import io.github.matheushenriquereiter.project.dtos.TaskRequestDTO;
import io.github.matheushenriquereiter.project.dtos.TaskResponseDTO;
import io.github.matheushenriquereiter.project.dtos.TaskStatusDTO;
import io.github.matheushenriquereiter.project.enums.TaskStatus;
import io.github.matheushenriquereiter.project.exceptions.EntityNotFoundException;
import io.github.matheushenriquereiter.project.models.TaskEntity;
import io.github.matheushenriquereiter.project.models.UserEntity;
import io.github.matheushenriquereiter.project.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        UserEntity userEntity = userService.getUserFromToken(jwtToken);

        return taskRepository.findAllByUser(userEntity)
                .stream()
                .sorted(Comparator.comparing(TaskEntity::getCreatedAt))
                .map(TaskEntity::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getPaginatedTasks(String jwtToken, int limit, int offset) {
        UserEntity userEntity = userService.getUserFromToken(jwtToken);
        Pageable pagination = PageRequest.of(offset, limit);

        return taskRepository.findAllByUser(userEntity, pagination)
                .stream()
                .map(TaskEntity::toResponseDTO)
                .toList();
    }

    public void createTask(String jwtToken, TaskRequestDTO taskRequestDTO) {
        UserEntity userEntity = userService.getUserFromToken(jwtToken);
        TaskEntity taskEntity = new TaskEntity(taskRequestDTO.title(), taskRequestDTO.description(), TaskStatus.TO_DO, userEntity);

        taskRepository.save(taskEntity);
    }

    public void deleteTask(String jwtToken, Long taskId) {
        UserEntity userEntity = userService.getUserFromToken(jwtToken);
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (!Objects.equals(userEntity.getId(), taskEntity.getUser().getId())) {
            throw new EntityNotFoundException("Task not found");
        }

        taskRepository.delete(taskEntity);
    }

    public void updateTask(String jwtToken, Long taskId, TaskRequestDTO taskRequestDTO) {
        UserEntity userEntity = userService.getUserFromToken(jwtToken);
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (!userEntity.getId().equals(taskEntity.getId())) {
            throw new EntityNotFoundException("Task not found");
        }

        taskEntity.setTitle(taskRequestDTO.title());
        taskEntity.setDescription(taskRequestDTO.description());

        taskRepository.save(taskEntity);
    }

    public void updateTaskStatus(String jwtToken, Long taskId, TaskStatusDTO taskStatusDTO) {
        UserEntity userEntity = userService.getUserFromToken(jwtToken);
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (!userEntity.getId().equals(taskEntity.getId())) {
            throw new EntityNotFoundException("Task not found");
        }

        taskEntity.setStatus(taskStatusDTO.status());
        taskRepository.save(taskEntity);
    }
}
