package br.com.gfctech.project_manager.entity;
import br.com.gfctech.project_manager.dto.TaskDTO;
import jakarta.persistence.*;
import lombok.*;

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
    private Float hours;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    private double accumulatedHours;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private UserEntity assignedUser;


    public TaskEntity(TaskDTO taskDTO) {
        this.id = taskDTO.getId();
        this.name = taskDTO.getName();
        this.description = taskDTO.getDescription();
        this.hours = taskDTO.getHours();
        this.status = taskDTO.getStatus();
    }
    
}
