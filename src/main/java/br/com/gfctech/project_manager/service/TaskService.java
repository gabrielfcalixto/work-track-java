package br.com.gfctech.project_manager.service;

import br.com.gfctech.project_manager.dto.TaskDTO;
import br.com.gfctech.project_manager.dto.TimeEntryDTO;
import br.com.gfctech.project_manager.entity.ProjectEntity;
import br.com.gfctech.project_manager.entity.TaskEntity;
import br.com.gfctech.project_manager.entity.TimeEntryEntity;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.enums.ProjectStatus;
import br.com.gfctech.project_manager.enums.TaskStatus;
import br.com.gfctech.project_manager.repository.ProjectRepository;
import br.com.gfctech.project_manager.repository.TaskRepository;
import br.com.gfctech.project_manager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(TaskDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id).map(TaskDTO::new)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));
    }

    @Transactional
    public TaskDTO addTask(TaskDTO taskDTO) {
        validateDates(taskDTO);
        ProjectEntity project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com ID: " + taskDTO.getProjectId()));

        Set<UserEntity> users = userRepository.findAllById(taskDTO.getAssignedUserIds()).stream().collect(Collectors.toSet());

        TaskEntity taskEntity = new TaskEntity(taskDTO, project, users);
        taskRepository.save(taskEntity);
        return new TaskDTO(taskEntity);
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        validateDates(taskDTO);
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));

        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setEstimatedHours(taskDTO.getEstimatedHours());
        task.setStatus(taskDTO.getStatus());
        task.setPriority(taskDTO.getPriority());
        task.setStartDate(taskDTO.getStartDate());
        task.setDeadline(taskDTO.getDeadline());

        Set<UserEntity> users = userRepository.findAllById(taskDTO.getAssignedUserIds()).stream().collect(Collectors.toSet());
        task.setAssignedUsers(users);

        return new TaskDTO(taskRepository.save(task));
    }

    @Transactional
    public TaskDTO updateTaskStatus(Long taskId, TaskStatus newStatus) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + taskId));

        if (task.getStatus() == TaskStatus.CONCLUIDA && newStatus != TaskStatus.CONCLUIDA) {
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

        UserEntity user = userRepository.getReferenceById(userId);
        task.getAssignedUsers().add(user);
        taskRepository.save(task);

        return new TaskDTO(task);
    }

    @Transactional
    public TaskDTO unassignUserFromTask(Long taskId, Long userId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + taskId));

        UserEntity user = userRepository.getReferenceById(userId);
        task.getAssignedUsers().remove(user);
        taskRepository.save(task);

        return new TaskDTO(task);
    }
    @Transactional
    public void recalculateTotalHours(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + taskId));
    
        // Usando o método correto para calcular as horas
        Double totalHours = task.getTimeEntries().stream()
                .mapToDouble(timeEntry -> timeEntry.getHoursLogged()) // Aqui você utiliza a propriedade de horas logadas
                .sum();
    
        task.setTotalHours(totalHours);
        taskRepository.save(task);
    }
    
    
    @Transactional
    private void updateProjectStatusIfNecessary(ProjectEntity project) {
        if (project == null) return;

        boolean allCompleted = project.getTasks().stream()
                .allMatch(task -> task.getStatus() == TaskStatus.CONCLUIDA);

        if (allCompleted && project.getStatus() != ProjectStatus.CONCLUIDO) {
            project.setStatus(ProjectStatus.CONCLUIDO);
            projectRepository.save(project);
        }
    }

    @Transactional
    public void deleteTask(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + taskId));

        if (task.getStatus() != TaskStatus.NAO_INICIADA) {
            throw new IllegalStateException("Não é possível excluir uma tarefa que já foi iniciada.");
        }

        taskRepository.delete(task);
    }

    public List<TaskDTO> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream().map(TaskDTO::new).collect(Collectors.toList());
    }

    private void validateDates(TaskDTO taskDTO) {
        if (taskDTO.getStartDate().isAfter(taskDTO.getDeadline())) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data limite.");
        }
    }
}