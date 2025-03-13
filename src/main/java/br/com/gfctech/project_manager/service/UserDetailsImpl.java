package br.com.gfctech.project_manager.service;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.entity.UserEntity.Role;

public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String name;
    private String login;
    private String email;
    private String password;
    private Role role; // O role é um enum
    private UserEntity user;

    public UserDetailsImpl(Long id, String name, String login, String password, String email, Role role, UserEntity user) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.user = user;
    }

    // Método para construir o UserDetailsImpl a partir de um UserEntity
    public static UserDetailsImpl build(UserEntity user) {
        // Adiciona o prefixo "ROLE_" ao role do enum para seguir o padrão do Spring Security
        Collection<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority(user.getRole().name())); 

        return new UserDetailsImpl(
            user.getId(),
            user.getName(),
            user.getLogin(),
            user.getPassword(),
            user.getEmail(),
            user.getRole(),
            user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getters adicionais
    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    // Método para formatar a data de ingresso (joinDate) no formato "dd-MM-yyyy"
    public String getJoinDate() {
        if (user.getJoinDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return user.getJoinDate().format(formatter);
        }
        return null;
    }
}
