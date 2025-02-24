package br.com.gfctech.project_manager.dto;
import br.com.gfctech.project_manager.entity.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private Float hours;
    private String status;
    private Long managerId; // Vincula o gestor responsável


    public ProjectDTO(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.description = projectEntity.getDescription();
        this.hours = projectEntity.getHours();
        this.status = projectEntity.getStatus();
        this.managerId = projectEntity.getManager() != null ? projectEntity.getManager().getId() : null;

    }

    
}
