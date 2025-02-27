package br.com.gfctech.project_manager.dto;

import java.time.LocalDate;

import br.com.gfctech.project_manager.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String login;
    private String password;
    private String email;
    private String role;
    private LocalDate joinDate;
    
    // Construtor completo
    public UserDTO(Long id, String name, String login, String password, String email, String role, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.joinDate = joinDate;
    }

    // Construtor que recebe UserEntity
    public UserDTO(UserEntity user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole().name(); // Converte Enum para String
        this.joinDate = user.getJoinDate();
    }
}
