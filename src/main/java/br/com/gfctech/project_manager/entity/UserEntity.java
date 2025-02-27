package br.com.gfctech.project_manager.entity;

import java.time.LocalDate;

import br.com.gfctech.project_manager.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GFC_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING) // Armazena a role como texto no banco
    @Column(nullable = false)
    private Role role; 

    @Column
    private LocalDate joinDate;

    // Construtor que recebe UserDTO
    public UserEntity(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.login = userDTO.getLogin();
        this.password = userDTO.getPassword();
        this.email = userDTO.getEmail();
        this.role = Role.valueOf(userDTO.getRole()); // Converte String para Enum
        this.joinDate = userDTO.getJoinDate();
    }

    // Enum de Role dentro da entidade
    public enum Role {
        ADMIN, MANAGER, USER, ;
    }
}
