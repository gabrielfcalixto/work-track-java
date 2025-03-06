package br.com.gfctech.project_manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

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

    // Método para calcular horas trabalhadas
    public Double getHoursLogged() {
        return (double) Duration.between(startTime, endTime).toMinutes() / 60;
    }
}
