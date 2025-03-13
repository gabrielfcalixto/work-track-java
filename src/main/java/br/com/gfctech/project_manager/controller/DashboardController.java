package br.com.gfctech.project_manager.controller;

import br.com.gfctech.project_manager.dto.DashboardDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.enums.TaskStatus;
import br.com.gfctech.project_manager.service.DashboardService;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("http://localhost:4200")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardDTO getDashboardData(@AuthenticationPrincipal UserEntity user) {
        return dashboardService.getDashboardData(user);
    }

    // ✅ Endpoint para obter horas totais de um usuário
    @GetMapping("/user-hours/{userId}")
    public Double getUserHours(@PathVariable Long userId) {
        return dashboardService.getUserHours(userId);
    }

    // ✅ Endpoint para obter horas totais lançadas no mês atual por um usuário
    @GetMapping("/user-hours-month/{userId}")
    public Double getTotalHoursMonth(@PathVariable Long userId) {
        return dashboardService.getTotalHoursMonth(userId);
    }

    // ✅ Endpoint para obter quantidade de tarefas pendentes de um usuário
    @GetMapping("/tasks/pending/{userId}")
    public Long getPendingTasksCount(@PathVariable Long userId) {
        return dashboardService.getPendingTasksCount(userId);
    }

    // ✅ Endpoint para obter quantidade de tarefas concluídas de um usuário
    @GetMapping("/tasks/completed/{userId}")
    public Long getCompletedTasksCount(@PathVariable Long userId) {
        return dashboardService.getCompletedTasksCount(userId);
    }

    // ✅ Endpoint para obter quantidade de tarefas em andamento de um usuário
    @GetMapping("/tasks/ongoing/{userId}")
    public Long getOngoingTasksCount(@PathVariable Long userId) {
        return dashboardService.getOngoingTasksCount(userId);
    }

    @GetMapping("/tasks/distribution/{userId}")
    public Map<TaskStatus, Long> getTaskDistribution(@PathVariable Long userId) {
        return dashboardService.getTaskDistribution(userId);
    }

}
