package br.com.gfctech.project_manager.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import br.com.gfctech.project_manager.entity.TimeEntryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntryDTO {

    private Long id;
    private Long taskId;
    private Long userId;  
    private String description;
    private LocalDate entryDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double hoursLogged;

    public TimeEntryDTO(TimeEntryEntity timeEntry) {
        if (timeEntry == null) {
            throw new IllegalArgumentException("TimeEntryEntity não pode ser nulo");
        }

        this.id = timeEntry.getId();
        this.taskId = (timeEntry.getTaskEntity() != null) ? timeEntry.getTaskEntity().getId() : null;
        this.userId = (timeEntry.getUserEntity() != null) ? timeEntry.getUserEntity().getId() : null;
        this.description = timeEntry.getDescription();
        this.entryDate = timeEntry.getEntryDate();
        this.startTime = timeEntry.getStartTime();
        this.endTime = timeEntry.getEndTime();
        this.hoursLogged = timeEntry.getHoursLogged(); // Usa o método da entidade para calcular
    }
}