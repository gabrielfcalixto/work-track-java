package br.com.gfctech.project_manager.dto;
import br.com.gfctech.project_manager.entity.TaskEntity;
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

public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Float hours;
    private String status;

    public TaskDTO(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.name = taskEntity.getName();
        this.description = taskEntity.getDescription();
        this.hours = taskEntity.getHours();
        this.status = taskEntity.getStatus();
    }

    
}
