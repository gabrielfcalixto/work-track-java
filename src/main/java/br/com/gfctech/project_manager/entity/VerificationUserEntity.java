package br.com.gfctech.project_manager.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="GFC_USER_VERIFICATION")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class VerificationUserEntity {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private UUID uuid;
	private Instant dateExpiration;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private UserEntity user;
	   
}
