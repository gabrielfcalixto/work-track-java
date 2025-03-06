package br.com.gfctech.project_manager.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.gfctech.project_manager.dto.TaskDTO;
import br.com.gfctech.project_manager.entity.TaskEntity;
import br.com.gfctech.project_manager.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired  
    private TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(TaskDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO addTask(TaskDTO taskDTO) {
        TaskEntity taskEntity = new TaskEntity(taskDTO);
        taskRepository.save(taskEntity);
        return new TaskDTO(taskEntity);
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        TaskEntity existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));

        existingTask.setName(taskDTO.getName());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setEstimatedHours(taskDTO.getEstimatedHours());
        existingTask.setStatus(taskDTO.getStatus());
        return new TaskDTO(taskRepository.save(existingTask));
    }

    @Transactional
    public TaskDTO updateStatus(Long id, String status) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
        task.setStatus(status);
        return new TaskDTO(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
        taskRepository.delete(task);
    }
}
