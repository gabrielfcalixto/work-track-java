package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.gfctech.project_manager.dto.ClientDTO;
import br.com.gfctech.project_manager.dto.ProjectDTO;
import br.com.gfctech.project_manager.service.ProjectService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/project")
@CrossOrigin(origins = "http://localhost:4200") // Permite requisições do frontend
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/add")
    public ProjectDTO addProject(@RequestBody ProjectDTO projectDTO) {  
        return projectService.addProject(projectDTO);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {  
        return projectService.updateProject(id, projectDTO);
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PatchMapping("/update-status/{id}")
    public ProjectDTO updateStatus(@PathVariable Long id, @RequestBody Map<String, String> update) {
        return projectService.updateStatus(id, update.get("status"));
    }


    @PreAuthorize("hasAnyRole( 'ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}