package br.com.gfctech.project_manager.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GFC_TIMEENTRY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TaskEntity taskEntity;

    @ManyToOne
    private UserEntity userEntity;

    private LocalDate entryDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private double totalHours;

    public void calculateTotalHours() {
        if (startTime != null && endTime != null) {
            long minutes = Duration.between(startTime, endTime).toMinutes();
            this.totalHours = minutes / 60.0;  
        } else {
            this.totalHours = 0;
        }
    }
}
