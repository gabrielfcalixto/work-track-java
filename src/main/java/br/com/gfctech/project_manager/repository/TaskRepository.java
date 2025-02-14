package br.com.gfctech.project_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.gfctech.project_manager.entity.TaskEntity;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    
}
