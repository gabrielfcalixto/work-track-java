package br.com.gfctech.project_manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private UserEntity userEntity;

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

    // Método para calcular horas trabalhadas
    public Double getHoursLogged() {
        if (startTime == null || endTime == null) {
            return 0.0;
        }
        Duration duration = Duration.between(startTime, endTime);
        return duration.toMinutes() / 60.0; // Converte minutos para horas
    }
        // Método para calcular e salvar totalHours antes de persistir ou atualizar
        @PrePersist
        @PreUpdate
        public void calculateTotalHours() {
            this.totalHours = getHoursLogged();
        }

        
}