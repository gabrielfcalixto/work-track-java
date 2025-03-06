package br.com.gfctech.project_manager.dto;

import br.com.gfctech.project_manager.entity.TaskEntity;
import lombok.*;

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
    private Double estimatedHours; // Alterado de Float para Double
    private String status;
    private Double totalHours; // Adicionado para refletir o total de horas calculado
    private ProjectDTO project;
    private Set<UserDTO> assignedUsers; // Corrigido para refletir múltiplos usuários

    public TaskDTO(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.name = taskEntity.getName();
        this.description = taskEntity.getDescription();
        this.estimatedHours = taskEntity.getEstimatedHours(); // Nome correto
        this.status = taskEntity.getStatus();
        this.totalHours = taskEntity.calculateTotalHours(); // Calcula o total de horas lançadas

        // Mapeia o projeto
        if (taskEntity.getProject() != null) {
            this.project = new ProjectDTO(taskEntity.getProject());
        }

        // Mapeia os usuários associados à tarefa
        if (taskEntity.getAssignedUsers() != null) {
            this.assignedUsers = taskEntity.getAssignedUsers().stream()
                                           .map(UserDTO::new)
                                           .collect(Collectors.toSet());
        }
    }
}
