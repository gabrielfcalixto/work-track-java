package br.com.gfctech.project_manager.dto;

import br.com.gfctech.project_manager.entity.TaskEntity;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.enums.TaskPriority;
import br.com.gfctech.project_manager.enums.TaskStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Double estimatedHours;
    private TaskStatus status;
    private TaskPriority priority;
    private Double totalHours;
    private Long projectId;
    private Set<Long> assignedUserIds;
    private LocalDate startDate;
    private LocalDate deadline;

    public TaskDTO(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.name = taskEntity.getName();
        this.description = taskEntity.getDescription();
        this.estimatedHours = taskEntity.getEstimatedHours();
        this.status = taskEntity.getStatus();
        this.priority = taskEntity.getPriority();
        this.totalHours = taskEntity.calculateTotalHours();
        this.projectId = taskEntity.getProject().getId();
        this.assignedUserIds = taskEntity.getAssignedUsers().stream()
                                         .map(UserEntity::getId)
                                         .collect(Collectors.toSet());
        this.startDate = taskEntity.getStartDate();
        this.deadline = taskEntity.getDeadline();
    }
}
