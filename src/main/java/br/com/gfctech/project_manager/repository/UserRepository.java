package br.com.gfctech.project_manager.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gfctech.project_manager.entity.TaskEntity;
import br.com.gfctech.project_manager.entity.UserEntity;


public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    Optional<TaskEntity> findByLogin(String login);
}
