package br.com.gfctech.project_manager.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.gfctech.project_manager.dto.ProjectDTO;
import br.com.gfctech.project_manager.entity.ClientEntity;
import br.com.gfctech.project_manager.entity.ProjectEntity;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.enums.ProjectStatus;
import br.com.gfctech.project_manager.repository.ClientRepository;
import br.com.gfctech.project_manager.repository.ProjectRepository;
import br.com.gfctech.project_manager.repository.UserRepository;

@Service
public class ProjectService {

    @Autowired  
    private ProjectRepository projectRepository;

    @Autowired  
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public ProjectDTO getProjectById(Long id) {
        return projectRepository.findById(id)
        .map(ProjectDTO::new)
        .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
    }

    @Transactional
    public ProjectDTO addProject(ProjectDTO projectDTO) {
        // Buscar o manager, client e teamMembers pelo ID
        UserEntity manager = userRepository.findById(projectDTO.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + projectDTO.getManagerId()));

        ClientEntity client = clientRepository.findById(projectDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + projectDTO.getClientId()));

        Set<UserEntity> teamMembers = userRepository.findAllById(projectDTO.getTeamMemberIds())
                .stream()
                .collect(Collectors.toSet());

        ProjectEntity projectEntity = new ProjectEntity(projectDTO, manager, client, teamMembers);

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

        // Converte a String para Enum
        ProjectStatus newStatus;
        try {
            newStatus = ProjectStatus.valueOf(status.toUpperCase()); // Converte para maiúsculas para evitar erros
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + status);
        }

        project.setStatus(newStatus); // Agora definimos corretamente o enum
        return new ProjectDTO(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(Long id) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
        projectRepository.delete(project);
    }
}
