package br.com.gfctech.project_manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.gfctech.project_manager.dto.TimeEntryDTO;

@Entity
@Table(name = "GFC_TIME_ENTRY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity taskEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate entryDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = true)
    private Double totalHours;


   // Método para calcular hoursLogged automaticamente
    public Double getHoursLogged() {
        if (startTime == null || endTime == null) {
            return 0.0; // Retorne 0.0 se startTime ou endTime for nulo
        }

        Duration duration = Duration.between(startTime, endTime);

        if (duration.isNegative()) {
            throw new IllegalArgumentException("O horário de término não pode ser anterior ao horário de início.");
        }

        return duration.toMinutes() / 60.0;
    }

// Método para atualizar totalHours automaticamente
    @PrePersist
    @PreUpdate
    private void calculateTotalHours() {
        this.totalHours = getHoursLogged();
    }
}