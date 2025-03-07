package br.com.gfctech.project_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.gfctech.project_manager.entity.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
