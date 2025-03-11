package br.com.gfctech.project_manager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gfctech.project_manager.entity.TaskEntity;

import java.util.List;

@Repository
public interface DashboardRepository extends CrudRepository<TaskEntity, Long> {

    @Query("SELECT SUM(te.totalHours) FROM TimeEntryEntity te WHERE te.user.id = :userId")
    Double getHorasTrabalhadas(@Param("userId") Long userId);

    @Query("SELECT t.name FROM TaskEntity t JOIN t.assignedUsers u WHERE u.id = :userId")
    List<String> getTasksUser(@Param("userId") Long userId);

    @Query("SELECT p.name FROM ProjectEntity p WHERE p.manager.id = :userId")
    List<String> getProjectsCriados(@Param("userId") Long userId);

    @Query("SELECT p.name, COUNT(t.id) FROM TaskEntity t JOIN t.project p WHERE p.manager.id = :userId GROUP BY p.name")
    List<Object[]> getTasksPorProject(@Param("userId") Long userId);

    @Query("SELECT COUNT(u.id) FROM UserEntity u")
    Long getUsersTotais();

    @Query("SELECT COUNT(p.id) FROM ProjectEntity p")
    Long getProjectsTotais();

    @Query("SELECT COUNT(t.id) FROM TaskEntity t")
    Long getTasksTotais();
}