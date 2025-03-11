package br.com.gfctech.project_manager.service;

import br.com.gfctech.project_manager.dto.DashboardDTO;
import br.com.gfctech.project_manager.entity.*;
import br.com.gfctech.project_manager.enums.TaskStatus;
import br.com.gfctech.project_manager.repository.ProjectRepository;
import br.com.gfctech.project_manager.repository.TaskRepository;
import br.com.gfctech.project_manager.repository.TimeEntryRepository;
import br.com.gfctech.project_manager.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
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
                dashboard.setTasksInProgress(
                        taskRepository.countByAssignedUsersAndStatus(user, TaskStatus.IN_PROGRESS)
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
}
