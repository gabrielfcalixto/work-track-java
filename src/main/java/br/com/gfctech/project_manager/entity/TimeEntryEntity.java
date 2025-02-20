package br.com.gfctech.project_manager.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
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
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity taskEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false)
    private LocalDate entryDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
    
    @Column(nullable = false)
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
