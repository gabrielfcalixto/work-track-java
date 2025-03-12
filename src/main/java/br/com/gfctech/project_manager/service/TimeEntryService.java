package br.com.gfctech.project_manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.gfctech.project_manager.dto.TimeEntryDTO;
import br.com.gfctech.project_manager.entity.TaskEntity;
import br.com.gfctech.project_manager.entity.TimeEntryEntity;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.repository.TaskRepository;
import br.com.gfctech.project_manager.repository.TimeEntryRepository;
import br.com.gfctech.project_manager.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class TimeEntryService {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @Autowired
    private UserRepository userRepository;  

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public TimeEntryDTO saveTimeEntry(TimeEntryDTO timeEntryDTO) {
        TimeEntryEntity timeEntry = toEntity(timeEntryDTO);
        timeEntry = timeEntryRepository.save(timeEntry);

        // Atualize o totalHours da tarefa
        TaskEntity task = timeEntry.getTaskEntity();
        task.setTotalHours(task.getTotalHours() + timeEntry.getHoursLogged());
        taskRepository.save(task);

        return new TimeEntryDTO(timeEntry);
    }

    public List<TimeEntryDTO> getTimeEntriesByUserId(Long userId) {
        List<TimeEntryEntity> entries = timeEntryRepository.findByUserId(userId);
        return entries.stream()
                      .map(TimeEntryDTO::new)
                      .collect(Collectors.toList());
    }

    public TimeEntryEntity toEntity(TimeEntryDTO timeEntryDTO) {
        TimeEntryEntity timeEntry = new TimeEntryEntity();
        timeEntry.setId(timeEntryDTO.getId());
        timeEntry.setEntryDate(timeEntryDTO.getEntryDate());
        timeEntry.setStartTime(timeEntryDTO.getStartTime());
        timeEntry.setEndTime(timeEntryDTO.getEndTime());
        timeEntry.setDescription(timeEntryDTO.getDescription());
    
        TaskEntity task = taskRepository.findById(timeEntryDTO.getTaskId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        UserEntity user = userRepository.findById(timeEntryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    
        timeEntry.setTaskEntity(task);
        timeEntry.setUser(user);
    
        // O campo totalHours será calculado automaticamente pelo método calculateTotalHours
        return timeEntry;
    }



}