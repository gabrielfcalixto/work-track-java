package br.com.gfctech.project_manager.controller;

import br.com.gfctech.project_manager.dto.DashboardDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.service.DashboardService;
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
    // Novo endpoint para obter as horas trabalhadas de um usuário
    @GetMapping("/user-hours/{userId}")
    public Double getUserHours(@PathVariable Long userId) {
        return dashboardService.getUserHours(userId);
    }
}
