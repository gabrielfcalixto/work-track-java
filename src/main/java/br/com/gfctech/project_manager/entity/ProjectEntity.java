package br.com.gfctech.project_manager.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.gfctech.project_manager.dto.ProjectDTO;
import br.com.gfctech.project_manager.enums.ProjectStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private UserEntity manager;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToMany
    @JoinTable(
        name = "project_team",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> teamMembers = new HashSet<>();

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate deadline;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskEntity> tasks = new ArrayList<>();

    public ProjectEntity(ProjectDTO projectDTO, UserEntity manager, ClientEntity client, Set<UserEntity> teamMembers) {
        this.id = projectDTO.getId();
        this.name = projectDTO.getName();
        this.description = projectDTO.getDescription();
        this.hours = projectDTO.getHours();
        this.status = projectDTO.getStatus();
        this.startDate = projectDTO.getStartDate();
        this.deadline = projectDTO.getDeadline();
        this.manager = manager;
        this.client = client;
        this.teamMembers = teamMembers;
    }
}