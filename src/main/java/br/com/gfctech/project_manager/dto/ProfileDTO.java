package br.com.gfctech.project_manager.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileDTO {

    private Long id;
    private String name;
    private String login;
    private String email;
    private String role;
    private LocalDate joinDate;

    public ProfileDTO(Long id, String name, String login, String email, String role, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.role = role;
        this.joinDate = joinDate;
    }
    
      
}
