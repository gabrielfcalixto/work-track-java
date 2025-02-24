package br.com.gfctech.project_manager.dto;

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

    private Long taskId;
    private Long userId;  
    private String description;
    private LocalDate entryDate;
    private LocalTime startTime;
    private LocalTime endTime;

     public TimeEntryDTO(TimeEntryEntity timeEntry) {
        this.taskId = timeEntry.getTaskEntity().getId();
        this.userId = timeEntry.getUserEntity().getId();  
        this.description = timeEntry.getDescription();  
        this.entryDate = timeEntry.getEntryDate();
        this.startTime = timeEntry.getStartTime();
        this.endTime = timeEntry.getEndTime();

    }
}
