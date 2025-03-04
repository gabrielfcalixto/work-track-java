package br.com.gfctech.project_manager.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.gfctech.project_manager.entity.UserEntity;


public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    UserDetails findByLogin(String login);
}
