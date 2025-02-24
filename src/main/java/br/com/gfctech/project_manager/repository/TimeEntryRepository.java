package br.com.gfctech.project_manager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gfctech.project_manager.entity.TimeEntryEntity;

public interface TimeEntryRepository extends JpaRepository<TimeEntryEntity, Long> {
        Optional<TimeEntryEntity> findById(Long id);
        List<TimeEntryEntity> findByUserEntityId(Long userId);

}
