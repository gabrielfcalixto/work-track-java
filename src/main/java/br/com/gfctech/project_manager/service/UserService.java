package br.com.gfctech.project_manager.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gfctech.project_manager.dto.UserDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.entity.VerificationUserEntity;
import br.com.gfctech.project_manager.entity.enums.TipoSituacaoUsuario;
import br.com.gfctech.project_manager.repository.UserRepository;
import br.com.gfctech.project_manager.repository.VerificationUserRepository;

@Service
public class UserService {

    @Autowired  
    private UserRepository userRepository;

	@Autowired
	private VerificationUserRepository verificationUserRepository;
	
	@Autowired(required = true)
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

	@Transactional
	public UserDTO addUser(UserDTO userDTO) {
		// Verificar se o e-mail já está registrado
		// if (userRepository.existsByEmail(userDTO.getEmail())) {
		// 	throw new RuntimeException("E-mail já está em uso");
		// }

		UserEntity userEntity = new UserEntity(userDTO);
		userEntity.setPassword(null); // Sem senha inicialmente
		userEntity.setSituacao(TipoSituacaoUsuario.PENDENTE);
		userRepository.save(userEntity);

		// Criar token para definição de senha
		VerificationUserEntity verification = new VerificationUserEntity();
		verification.setUser(userEntity);
		verification.setUuid(UUID.randomUUID());
		verification.setDateExpiration(Instant.now().plusMillis(900000)); // 15 minutos de validade
		verificationUserRepository.save(verification);

		// Enviar e-mail para o usuário definir a senha
		String link = "http://localhost:4200/definir-senha?token=" + verification.getUuid();
		emailService.enviarEmailTexto(
				userDTO.getEmail(),
				"Defina sua senha",
				"Clique no link abaixo para definir sua senha:\n" + link);

		return new UserDTO(userEntity);
	}


	@Transactional
	public String definirSenha(String token, String newPassword) {
		VerificationUserEntity verification = verificationUserRepository.findByUuid(UUID.fromString(token))
				.orElseThrow(() -> new RuntimeException("Token inválido ou expirado"));

		UserEntity user = verification.getUser();
		user.setPassword(passwordEncoder.encode(newPassword)); // 🔹 Criptografa a senha
		user.setSituacao(TipoSituacaoUsuario.ATIVO); // Ativa o usuário
		userRepository.save(user);

		verificationUserRepository.delete(verification); // Remove o token usado

		return "Senha definida com sucesso!";
	}

	
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existingUser.setName(userDTO.getName());
        existingUser.setLogin(userDTO.getLogin());
        existingUser.setEmail(userDTO.getEmail());
        // Atualize outros campos conforme necessário
        return new UserDTO(userRepository.save(existingUser));
    }

    @Transactional
    public UserDTO updatePermissions(Long id, String role) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return new UserDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        userRepository.delete(user);
    }
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
            .map(UserDTO::new) // Usa o construtor corrigido
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    
		
	public String verificarCadastro(String uuid) {
		Optional<VerificationUserEntity> verificationUserOpt = verificationUserRepository.findByUuid(UUID.fromString(uuid));
		
		if (verificationUserOpt.isPresent()) {
			VerificationUserEntity verificationUser = verificationUserOpt.get();
			
			if (verificationUser.getDateExpiration().compareTo(Instant.now()) >= 0) {
				UserEntity user = verificationUser.getUser();
				user.setSituacao(TipoSituacaoUsuario.ATIVO);
				userRepository.save(user);
				return "Usuário Verificado";
			} else {
				verificationUserRepository.delete(verificationUser);
				return "Tempo de verificação expirado";
			}
		} else {
			return "Usuário não verificado";
		}
	}
	

	public UserDTO alterar(UserDTO user) {
		UserEntity userEntity = new UserEntity(user);
		return new UserDTO(userRepository.save(userEntity));
	}
	
	public void excluir(Long id) {
		UserEntity user = userRepository.findById(id).get();
		userRepository.delete(user);
	}
	
	public UserDTO buscarPorId(Long id) {
		return new UserDTO(userRepository.findById(id).get());
	}
}
