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
import br.com.gfctech.project_manager.entity.UserEntity.Role;
import br.com.gfctech.project_manager.entity.enums.TipoSituacaoUsuario;
import br.com.gfctech.project_manager.repository.UserRepository;
import br.com.gfctech.project_manager.repository.VerificationUserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationUserRepository verificationUserRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para criar um usuário (somente admin pode criar usuários)
    public void addUser(UserDTO addUser, Role role) {
        // Verifica se o papel do usuário é ADMIN antes de permitir a criação
        if (!role.equals(Role.ADMIN)) {
            throw new RuntimeException("Apenas administradores podem criar novos usuários.");
        }

        // Cria a entidade do usuário
        UserEntity user = new UserEntity();
        user.setName(addUser.getName());
        user.setLogin(addUser.getLogin());
        user.setEmail(addUser.getEmail());
        user.setRole(Role.USER);  // Define o papel como 'USER' por padrão
        user.setSituacao(TipoSituacaoUsuario.PENDENTE); // A situação pode ser pendente até ativação

        // Salva o usuário no banco de dados
        userRepository.save(user);

        // Gera um token de verificação (UUID)
        VerificationUserEntity verificationUser = new VerificationUserEntity();
        verificationUser.setUuid(UUID.randomUUID());
        verificationUser.setDateExpiration(Instant.now().plusSeconds(86400)); // Expira em 24 horas
        verificationUser.setUser(user);

        // Salva o token no banco de dados
        verificationUserRepository.save(verificationUser);

        // Envia o e-mail com o link de ativação
        String assunto = "Ative sua conta";
        String mensagem = "Clique no link abaixo para definir sua senha:\n"
                        + "http://seusistema.com/auth/definir-senha?token=" + verificationUser.getUuid();

        emailService.enviarEmailTexto(user.getEmail(), assunto, mensagem);
    }

    // Método para definir a senha (mantido o mesmo)
    public String definirSenha(String token, String newPassword) {
        // Busca o token no banco de dados
        VerificationUserEntity verificationUser = verificationUserRepository.findByUuid(UUID.fromString(token))
                .orElseThrow(() -> new RuntimeException("Token inválido ou expirado"));

        // Verifica se o token expirou
        if (verificationUser.getDateExpiration().isBefore(Instant.now())) {
            throw new RuntimeException("Token expirado");
        }

        // Busca o usuário associado ao token
        UserEntity user = verificationUser.getUser();

        // Criptografa a nova senha
        user.setPassword(passwordEncoder.encode(newPassword));

        // Altera o status do usuário para ATIVO
        user.setSituacao(TipoSituacaoUsuario.ATIVO);

        // Salva o usuário atualizado
        userRepository.save(user);

        // Remove o token de verificação (opcional)
        verificationUserRepository.delete(verificationUser);

        return "Senha definida com sucesso!";
    }
		public String verificarCadastro(String uuid) {
			// Busca o token no banco de dados
			VerificationUserEntity verificationUser = verificationUserRepository.findByUuid(UUID.fromString(uuid))
					.orElseThrow(() -> new RuntimeException("Token inválido"));
	
			// Retorna uma mensagem de sucesso
			return "Token válido. Agora você pode definir sua senha.";
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

	public List<UserDTO> getAllUsers() {
		List<UserEntity> users = userRepository.findAll(); // Obtém todos os usuários do repositório
		return users.stream() // Cria um fluxo de usuários
					.map(user -> new UserDTO(user)) // Mapeia cada usuário para um UserDTO
					.collect(Collectors.toList()); // Coleta os DTOs em uma lista
	}
	
}

