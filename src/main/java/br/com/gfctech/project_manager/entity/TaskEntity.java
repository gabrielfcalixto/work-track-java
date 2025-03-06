package br.com.gfctech.project_manager.entity;

import br.com.gfctech.project_manager.dto.TaskDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GFC_TASK")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double estimatedHours; // Definido quando a tarefa é criada

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double totalHours = 0.0; // Calculado com base nas horas lançadas

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    // Relação ManyToMany para associar vários usuários à mesma tarefa
    @ManyToMany
    @JoinTable(
        name = "GFC_TASK_USER",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> assignedUsers = new HashSet<>();

    @OneToMany(mappedBy = "taskEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TimeEntryEntity> timeEntries = new HashSet<>();

    public TaskEntity(TaskDTO taskDTO) {
        this.id = taskDTO.getId();
        this.name = taskDTO.getName();
        this.description = taskDTO.getDescription();
        this.estimatedHours = taskDTO.getEstimatedHours();
        this.status = taskDTO.getStatus();
        this.project = new ProjectEntity(taskDTO.getProjectDTO());
    }

    // Método para calcular total de horas automaticamente
    public Double calculateTotalHours() {
        return timeEntries.stream()
                          .mapToDouble(TimeEntryEntity::getHoursLogged)
                          .sum();
    }
}
