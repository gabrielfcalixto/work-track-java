package br.com.gfctech.project_manager.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gfctech.project_manager.entity.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

}