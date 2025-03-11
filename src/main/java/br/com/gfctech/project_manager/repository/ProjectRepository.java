package br.com.gfctech.project_manager.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gfctech.project_manager.entity.ProjectEntity;
import br.com.gfctech.project_manager.entity.UserEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    Long countByManager(UserEntity manager);
    
    @Query("SELECT COUNT(p) FROM ProjectEntity p WHERE p.manager = :manager")
    Long countProjectsByManager(@Param("manager") UserEntity manager);

}