package br.com.gfctech.project_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gfctech.project_manager.entity.TaskEntity;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT t FROM TaskEntity t JOIN t.assignedUsers u WHERE u.id = :userId")
    List<TaskEntity> findByUserId(@Param("userId") Long userId);
}
