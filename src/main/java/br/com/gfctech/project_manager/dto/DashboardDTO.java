package br.com.gfctech.project_manager.dto;

import lombok.Data;

@Data
public class DashboardDTO {
    private Double totalHoursWorked;  // Para usuários comuns
    private Long tasksInProgress;

    private Long projectsManaged;  // Para gestores
    private Long tasksCreated;
    private Long usersManaged;

    private Long totalUsers;  // Para admins
    private Long totalProjects;
    private Long totalTasks;
}
