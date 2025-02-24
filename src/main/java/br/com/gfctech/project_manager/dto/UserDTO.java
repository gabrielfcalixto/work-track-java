package br.com.gfctech.project_manager.dto;

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
    

    public UserDTO(UserEntity user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.email = user.getEmail();
    }
}
