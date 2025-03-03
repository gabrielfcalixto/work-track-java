package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    public ProjectDTO addProject(@RequestBody ProjectDTO projectDTO) {  
        return projectService.addProject(projectDTO);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {  
        return projectService.updateProject(id, projectDTO);
    }

    @PatchMapping("/{id}/status")
    public ProjectDTO updateStatus(@PathVariable Long id, @RequestBody Map<String, String> update) {
        return projectService.updateStatus(id, update.get("status"));
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}