package br.com.gfctech.project_manager.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gfctech.project_manager.entity.UserEntity;


public interface UserRepository  extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByLogin (String login);
  // Method to check if a user exists by email
	// boolean existsByEmail(String email);

	Optional<UserEntity> findByEmail(String email);



}
