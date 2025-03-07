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

@Service
public class TimeEntryService {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @Autowired
    private UserRepository userRepository;  

    @Autowired
    private TaskRepository taskRepository;

    public TimeEntryEntity saveTimeEntry(TimeEntryDTO timeEntryDTO) {
        // Validação de horários
        if (timeEntryDTO.getStartTime().isAfter(timeEntryDTO.getEndTime())) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de término.");
        }

        TimeEntryEntity timeEntry = new TimeEntryEntity();
        
        TaskEntity task = taskRepository.findById(timeEntryDTO.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));            

        UserEntity user = userRepository.findById(timeEntryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        timeEntry.setTaskEntity(task);
        timeEntry.setUserEntity(user);  
        timeEntry.setEntryDate(timeEntryDTO.getEntryDate());
        timeEntry.setStartTime(timeEntryDTO.getStartTime());
        timeEntry.setEndTime(timeEntryDTO.getEndTime());
        timeEntry.setDescription(timeEntryDTO.getDescription());

        // Salva o lançamento
        timeEntry = timeEntryRepository.save(timeEntry);

        // Atualiza as horas totais da tarefa
        task.setTotalHours(task.getTotalHours() + timeEntry.getHoursLogged());
        taskRepository.save(task);

        return timeEntry;
    }

    // Método para buscar os lançamentos de um usuário
    public List<TimeEntryDTO> getTimeEntriesByUserId(Long userId) {
        List<TimeEntryEntity> entries = timeEntryRepository.findByUserEntityId(userId);
        return entries.stream()
                      .map(TimeEntryDTO::new)
                      .collect(Collectors.toList());
    }
}