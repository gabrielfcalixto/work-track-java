package br.com.gfctech.project_manager.entity;

import br.com.gfctech.project_manager.dto.TaskDTO;
import br.com.gfctech.project_manager.enums.TaskPriority;
import br.com.gfctech.project_manager.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private Double estimatedHours; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority; 

    @Column(nullable = false)
    private Double totalHours = 0.0; 
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToMany
    @JoinTable(
        name = "GFC_TASK_USER",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> assignedUsers = new HashSet<>();

    @OneToMany(mappedBy = "taskEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TimeEntryEntity> timeEntries = new HashSet<>();

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate deadline;

    public TaskEntity(TaskDTO taskDTO, ProjectEntity project, Set<UserEntity> users) {
        this.id = taskDTO.getId();
        this.name = taskDTO.getName();
        this.description = taskDTO.getDescription();
        this.estimatedHours = taskDTO.getEstimatedHours();
        this.status = taskDTO.getStatus();
        this.priority = taskDTO.getPriority();
        this.startDate = taskDTO.getStartDate();
        this.deadline = taskDTO.getDeadline();
        this.project = project;
        this.assignedUsers = users;
        this.totalHours = calculateTotalHours();
    }

    public Double calculateTotalHours() {
        return timeEntries.stream()
                          .mapToDouble(TimeEntryEntity::getHoursLogged)
                          .sum();
    }
}