package br.com.gfctech.project_manager.service;

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
        TimeEntryEntity timeEntry = new TimeEntryEntity();
        
        TaskEntity task = taskRepository.findById(timeEntryDTO.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));            
        // Corrigindo a busca do usuário 

        UserEntity user = userRepository.findById(timeEntryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        timeEntry.setTaskEntity(task);
        timeEntry.setUserEntity(user);  
        timeEntry.setEntryDate(timeEntryDTO.getEntryDate());
        timeEntry.setStartTime(timeEntryDTO.getStartTime());
        timeEntry.setEndTime(timeEntryDTO.getEndTime());

        // Calculando o total de horas automaticamente
        timeEntry.calculateTotalHours();

        //atualizando as horas acumuladas
        double newAccumulatedHours = task.getAccumulatedHours() + timeEntry.getTotalHours();
        task.setAccumulatedHours(newAccumulatedHours);

        //salvando o lançamento e atualizando tarefa
        timeEntry = timeEntryRepository.save(timeEntry);
        taskRepository.save(task);

        return timeEntry;
    }
}
