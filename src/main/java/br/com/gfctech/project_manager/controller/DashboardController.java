package br.com.gfctech.project_manager.controller;

import br.com.gfctech.project_manager.dto.DashboardDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.service.DashboardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardDTO getDashboardData(@AuthenticationPrincipal UserEntity user) {
        return dashboardService.getDashboardData(user);
    }
}
