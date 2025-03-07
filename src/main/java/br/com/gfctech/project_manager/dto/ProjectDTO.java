package br.com.gfctech.project_manager.dto;

import br.com.gfctech.project_manager.entity.ProjectEntity;
import br.com.gfctech.project_manager.enums.ProjectStatus;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private Float hours;
    private ProjectStatus status;
    private Long managerId;
    private Long clientId;
    private Set<Long> teamMemberIds;
    private LocalDate startDate;
    private LocalDate deadline;

    public ProjectDTO(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.description = projectEntity.getDescription();
        this.hours = projectEntity.getHours();
        this.status = projectEntity.getStatus();
        this.managerId = projectEntity.getManager() != null ? projectEntity.getManager().getId() : null;
        this.clientId = projectEntity.getClient() != null ? projectEntity.getClient().getId() : null;
        this.teamMemberIds = projectEntity.getTeamMembers() != null 
                ? projectEntity.getTeamMembers().stream().map(member -> member.getId()).collect(Collectors.toSet()) 
                : null;
        this.startDate = projectEntity.getStartDate();
        this.deadline = projectEntity.getDeadline();
    }
}
