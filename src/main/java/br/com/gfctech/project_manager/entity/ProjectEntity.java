package br.com.gfctech.project_manager.entity;
import br.com.gfctech.project_manager.dto.ProjectDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GFC_PROJECTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {
    
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

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private UserEntity manager;

    public ProjectEntity(ProjectDTO projectDTO) {
        this.id = projectDTO.getId();
        this.name = projectDTO.getName();
        this.description = projectDTO.getDescription();
        this.hours = projectDTO.getHours();
        this.status = projectDTO.getStatus();
    }
}
