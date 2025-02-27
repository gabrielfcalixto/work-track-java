package br.com.gfctech.project_manager.service;

import java.time.Instant;
import java.util.List;
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
        UserEntity userEntity = new UserEntity(userDTO);
        userRepository.save(userEntity);
        return new UserDTO(userEntity);
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
    public void addNewUser(UserDTO user) {
		UserEntity userEntity = new UserEntity(user);
		userEntity. setSituacao(TipoSituacaoUsuario.PENDENTE); 
		userEntity.setId(null);
		userRepository.save(userEntity);
		
		VerificationUserEntity verificador = new VerificationUserEntity();
		verificador.setUser(userEntity);
		verificador.setUuid(UUID.randomUUID());
		verificador.setDateExpiration(Instant.now().plusMillis(900000));
		verificationUserRepository.save(verificador);
		
		emailService.enviarEmailTexto(user.getEmail(),
				"Novo usuário cadastrado",
				"Você está recebendo um email de cadastro o número para validação é " + verificador.getUuid());
	}
		
	public String verificarCadastro(String uuid) {
		VerificationUserEntity verificationUser =  verificationUserRepository.findByUuid(UUID.fromString(uuid)).get();
		
		if(verificationUser != null) {
			if(verificationUser.getDateExpiration().compareTo(Instant.now()) >= 0) {	
				UserEntity u = verificationUser.getUser();
				u.setSituacao(TipoSituacaoUsuario.ATIVO);
				userRepository.save(u);
				return "Usuario Verificado";				
			}else {
					verificationUserRepository.delete(verificationUser);		
					return "Tempo de verificação expirado";
			} 
		}else {
			return "Usuario não verificado";
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
