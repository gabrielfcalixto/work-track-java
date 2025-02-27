package br.com.gfctech.project_manager.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gfctech.project_manager.entity.VerificationUserEntity;


public interface VerificationUserRepository extends JpaRepository<VerificationUserEntity, Long>{
	public Optional<VerificationUserEntity> findByUuid(UUID uuid);
}
