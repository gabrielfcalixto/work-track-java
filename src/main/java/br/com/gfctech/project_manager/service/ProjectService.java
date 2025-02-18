package br.com.gfctech.project_manager.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gfctech.project_manager.dto.ProjectDTO;
import br.com.gfctech.project_manager.entity.ProjectEntity;
import br.com.gfctech.project_manager.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired  
    private ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public ProjectDTO addProject(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = new ProjectEntity(projectDTO);
        projectRepository.save(projectEntity);
        return new ProjectDTO(projectEntity);
    }

    @Transactional
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        ProjectEntity existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        existingProject.setName(projectDTO.getName());
        existingProject.setDescription(projectDTO.getDescription());
        existingProject.setHours(projectDTO.getHours());
        existingProject.setStatus(projectDTO.getStatus());
        return new ProjectDTO(projectRepository.save(existingProject));
    }

    @Transactional
    public ProjectDTO updateStatus(Long id, String status) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
        project.setStatus(status);
        return new ProjectDTO(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(Long id) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
        projectRepository.delete(project);
    }
}
