package br.com.gfctech.project_manager.service;

import br.com.gfctech.project_manager.dto.DashboardDTO;
import br.com.gfctech.project_manager.entity.*;
import br.com.gfctech.project_manager.enums.TaskStatus;
import br.com.gfctech.project_manager.repository.ProjectRepository;
import br.com.gfctech.project_manager.repository.TaskRepository;
import br.com.gfctech.project_manager.repository.TimeEntryRepository;
import br.com.gfctech.project_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TimeEntryRepository timeEntryRepository;
    private final UserRepository userRepository;

    public DashboardService(ProjectRepository projectRepository, TaskRepository taskRepository, 
                            TimeEntryRepository timeEntryRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.timeEntryRepository = timeEntryRepository;
        this.userRepository = userRepository;
    }

    public DashboardDTO getDashboardData(UserEntity user) {
        DashboardDTO dashboard = new DashboardDTO();

        switch (user.getRole()) {
            case USER:
                dashboard.setTotalHoursWorked(
                        timeEntryRepository.sumHoursByUser(user.getId())
                );
                dashboard.setProjectsManaged(
                    projectRepository.countProjectsByManager(user)
                );

                break;

            case MANAGER:
                dashboard.setProjectsManaged(
                        projectRepository.countByManager(user)
                );
                dashboard.setTasksCreated(
                        taskRepository.countByProjectManager(user)
                );
                dashboard.setProjectsManaged(
                        projectRepository.countProjectsByManager(user)
                    );

                break;

            case ADMIN:
                dashboard.setTotalUsers(userRepository.count());
                dashboard.setTotalProjects(projectRepository.count());
                dashboard.setTotalTasks(taskRepository.count());
                break;
        }

        return dashboard;
    }

    public Double getUserHours(Long userId) {
        return timeEntryRepository.sumHoursByUser(userId);
    }
    
    public Double getTotalHoursMonth(Long userId) {
        // Lógica para calcular as horas lançadas no mês atual
        LocalDate currentDate = LocalDate.now();
        return timeEntryRepository.sumHoursForUserInMonth(userId, currentDate.getMonthValue(), currentDate.getYear());
    }
    

    public Long getPendingTasksCount(Long userId) {
        // Lógica para contar as tarefas pendentes
        return taskRepository.countPendingTasks(userId);
    }

    public Long getCompletedTasksCount(Long userId) {
        // Lógica para contar as tarefas completadas
        return taskRepository.countCompletedTasks(userId);
    }

    public Long getOngoingTasksCount(Long userId) {
        // Lógica para contar as tarefas em andamento
        return taskRepository.countOngoingTasks(userId);
    }

    public Map<TaskStatus, Long> getTaskDistribution(Long userId) {
    List<Object[]> results = taskRepository.countTasksByStatus(userId);
    Map<TaskStatus, Long> taskDistribution = new EnumMap<>(TaskStatus.class);

    // Inicializa com 0 pra garantir que todos os status apareçam
    for (TaskStatus status : TaskStatus.values()) {
        taskDistribution.put(status, 0L);
    }

    // Popula com os dados reais
    for (Object[] result : results) {
        TaskStatus status = (TaskStatus) result[0];
        Long count = (Long) result[1];
        taskDistribution.put(status, count);
    }

    return taskDistribution;
}




}
