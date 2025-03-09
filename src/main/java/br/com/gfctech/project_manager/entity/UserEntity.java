package br.com.gfctech.project_manager.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.gfctech.project_manager.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "GFC_USER")
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

    @Column(nullable = true)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDate joinDate = LocalDate.now();

    // codigo para redefinir senha
    @Column(name = "reset_password_code")
    private String resetPasswordCode;

    @Column(name = "code_expiration_date")
    private LocalDateTime codeExpirationDate;


    // Enum de Role dentro da entidade
    public enum Role {
        ADMIN, MANAGER, USER
    }

    public UserEntity(UserDTO userDTO) {
        if (userDTO != null) {
            this.id = userDTO.getId();
            this.name = userDTO.getName();
            this.login = userDTO.getLogin();
            this.password = userDTO.getPassword();
            this.email = userDTO.getEmail();
            this.role = (userDTO.getRole() != null) ? Role.valueOf(userDTO.getRole()) : null;
            this.joinDate = (userDTO.getJoinDate() != null) ? userDTO.getJoinDate() : LocalDate.now();
        }
    }
    


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UserEntity other = (UserEntity) obj;
        return Objects.equals(id, other.id);
    }
}