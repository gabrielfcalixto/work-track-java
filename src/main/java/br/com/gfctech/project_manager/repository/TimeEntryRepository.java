package br.com.gfctech.project_manager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gfctech.project_manager.entity.TimeEntryEntity;

public interface TimeEntryRepository extends JpaRepository<TimeEntryEntity, Long> {
        Optional<TimeEntryEntity> findById(Long id);

        List<TimeEntryEntity> findByUserId(Long userId);

        @Query("SELECT SUM(te.totalHours) FROM TimeEntryEntity te WHERE te.user.id = :userId")
        Double sumHoursByUser(@Param("userId") Long userId);

        @Query(value = "SELECT SUM(te.total_hours) FROM GFC_TIME_ENTRY te WHERE te.user_id = :userId AND MONTH(te.entry_date) = :month AND YEAR(te.entry_date) = :year", nativeQuery = true)
        Double sumHoursForUserInMonth(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);

}


