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

    public TimeEntryDTO saveTimeEntry(TimeEntryDTO timeEntryDTO) {
        // Validação de horários
        if (timeEntryDTO.getStartTime().isAfter(timeEntryDTO.getEndTime())) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de término.");
        }
    
        // Converte o DTO para Entity
        TimeEntryEntity timeEntry = toEntity(timeEntryDTO);
    
        // Salva o lançamento
        timeEntry = timeEntryRepository.save(timeEntry);
    
        // Atualiza as horas totais da tarefa
        TaskEntity task = timeEntry.getTaskEntity();
        task.setTotalHours(task.getTotalHours() + timeEntry.getHoursLogged());
        taskRepository.save(task);
    
        // Retorna o DTO convertido
        return new TimeEntryDTO(timeEntry);
    }

    // Método para buscar os lançamentos de um usuário
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

        // Busca a tarefa e o usuário no banco de dados
        TaskEntity task = taskRepository.findById(timeEntryDTO.getTaskId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        UserEntity user = userRepository.findById(timeEntryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        timeEntry.setTaskEntity(task);
        timeEntry.setUser(user);

        return timeEntry;
    }



}