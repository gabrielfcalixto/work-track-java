package br.com.gfctech.project_manager.service;

import br.com.gfctech.project_manager.dto.TaskDTO;
import br.com.gfctech.project_manager.entity.ProjectEntity;
import br.com.gfctech.project_manager.entity.TaskEntity;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.enums.ProjectStatus;
import br.com.gfctech.project_manager.enums.TaskStatus;
import br.com.gfctech.project_manager.repository.ProjectRepository;
import br.com.gfctech.project_manager.repository.TaskRepository;
import br.com.gfctech.project_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskDTO::new)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));
    }

    @Transactional
    public TaskDTO addTask(TaskDTO taskDTO) {
        ProjectEntity project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com ID: " + taskDTO.getProjectId()));

        Set<UserEntity> users = userRepository.findAllById(taskDTO.getAssignedUserIds()).stream()
                .collect(Collectors.toSet());

        TaskEntity taskEntity = new TaskEntity(taskDTO, project, users);
        taskRepository.save(taskEntity);
        return new TaskDTO(taskEntity);
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));

        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setEstimatedHours(taskDTO.getEstimatedHours());
        task.setStatus(taskDTO.getStatus());
        task.setPriority(taskDTO.getPriority());
        task.setStartDate(taskDTO.getStartDate());
        task.setDeadline(taskDTO.getDeadline());

        Set<UserEntity> users = userRepository.findAllById(taskDTO.getAssignedUserIds()).stream()
                .collect(Collectors.toSet());
        task.setAssignedUsers(users);

        return new TaskDTO(taskRepository.save(task));
    }

    @Transactional
    public TaskDTO updateTaskStatus(Long taskId, TaskStatus newStatus) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + taskId));

        if (task.getStatus() == TaskStatus.COMPLETED && newStatus != TaskStatus.COMPLETED) {
            throw new IllegalStateException("Não é possível reabrir uma tarefa já concluída.");
        }

        task.setStatus(newStatus);
        taskRepository.save(task);

        updateProjectStatusIfNecessary(task.getProject());

        return new TaskDTO(task);
    }
    @Transactional
    public TaskDTO assignTaskToUser(Long taskId, Long userId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + taskId));
    
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
    
        // Certifique-se de carregar a coleção primeiro, antes de modificar
        Set<UserEntity> assignedUsers = task.getAssignedUsers();
        assignedUsers.add(user); // Atribuindo usuário de forma segura
    
        taskRepository.save(task);
    
        return new TaskDTO(task);
    }
    
    

    @Transactional
    public TaskDTO unassignUserFromTask(Long taskId, Long userId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + taskId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        task.getAssignedUsers().remove(user);
        taskRepository.save(task);

        return new TaskDTO(task);
    }

    @Transactional
    public void recalculateTotalHours(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + taskId));

        Double totalHours = task.calculateTotalHours();
        task.setTotalHours(totalHours);
        taskRepository.save(task);
    }

    @Transactional
    private void updateProjectStatusIfNecessary(ProjectEntity project) {
        if (project == null) return;

        List<TaskEntity> tasks = project.getTasks() != null 
        ? new ArrayList<>(project.getTasks()) 
        : new ArrayList<>();
    
        boolean allCompleted = tasks.stream()
                .allMatch(task -> task.getStatus() == TaskStatus.COMPLETED);

        if (allCompleted && project.getStatus() != ProjectStatus.COMPLETED) {
            project.setStatus(ProjectStatus.COMPLETED);
            projectRepository.save(project);
        }
    }


    @Transactional
    public void deleteTask(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + taskId));

        if (task.getStatus() != TaskStatus.NOT_STARTED) {
            throw new IllegalStateException("Não é possível excluir uma tarefa que já foi iniciada.");
        }

        taskRepository.delete(task);
    }

    public List<TaskDTO> getTasksByUserId(Long userId) {
        List<TaskEntity> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                    .map(TaskDTO::new) // Converte para DTO
                    .collect(Collectors.toList());
    }
}