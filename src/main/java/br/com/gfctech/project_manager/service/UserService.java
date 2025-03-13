package br.com.gfctech.project_manager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.gfctech.project_manager.dto.UserDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.entity.UserEntity.Role;
import br.com.gfctech.project_manager.exceptions.UsuarioNaoEncontradoException;
import br.com.gfctech.project_manager.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

	public List<UserDTO> getAllUsers() {
		List<UserEntity> users = userRepository.findAll(); // Obtém todos os usuários do repositório
		return users.stream() // Cria um fluxo de usuários
					.map(user -> new UserDTO(user)) // Mapeia cada usuário para um UserDTO
					.collect(Collectors.toList()); // Coleta os DTOs em uma lista
	}

    public void addUser(UserDTO addUser) {

        if(userRepository.findByEmail(addUser.getEmail()).isPresent()){
            throw new IllegalArgumentException("O e-mail já está em uso.");
        }

        String senhaGerada = RandomStringUtils.randomAlphanumeric(8); // Gera uma senha aleatória
        // Cria a entidade do usuário
        UserEntity user = new UserEntity();
        user.setName(addUser.getName());
        user.setLogin(addUser.getLogin());
        user.setEmail(addUser.getEmail());
        user.setRole(Role.valueOf(addUser.getRole()));
        user.setPassword(passwordEncoder.encode(senhaGerada));
        userRepository.save(user);

        // Envia a senha no email para o usuário
        String assunto = "Login no HardProject";
        String mensagem = "Olá Sr(a). " + user.getName() + ",\n\n"
                + "Seu cadastro foi realizado com sucesso no HardProject. Segue abaixo sua senha para entrar no sistema:\n\n"
                + "Senha: " + senhaGerada + "\n\n"
                + "Recomendamos que você altere sua senha após o primeiro login.";

        emailService.enviarEmailTexto(user.getEmail(), assunto, mensagem);
    }
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existingUser.setName(userDTO.getName());
        existingUser.setLogin(userDTO.getLogin());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(Role.valueOf(userDTO.getRole())); 
        existingUser.setPassword(userDTO.getPassword());
        // Atualize outros campos conforme necessário
        return new UserDTO(userRepository.save(existingUser));
    }

    @Transactional
    public UserDTO updatePermissions(Long id, String role) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        try {
            // Converte a String recebida para Enum (garante que está maiúscula)
            user.setRole(UserEntity.Role.valueOf(role.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + role + ". Allowed values: ADMIN, MANAGER, USER");
        }
    
        return new UserDTO(userRepository.save(user));
    }
    

    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
        userRepository.delete(user);
    }
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
            .map(UserDTO::new) // Usa o construtor corrigido
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        // Recupera o usuário pelo ID
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + userId));
        
        // Verifica se a senha antiga fornecida está correta
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("A senha antiga fornecida está incorreta.");
        }

        // Verifica se a nova senha é diferente da atual
        if (oldPassword.equals(newPassword)) {
            throw new RuntimeException("A nova senha deve ser diferente da senha antiga.");
        }

        // Codifica a nova senha
        user.setPassword(passwordEncoder.encode(newPassword));

        // Salva o usuário com a nova senha
        userRepository.save(user);

        // Envia e-mail de confirmação (opcional)
        String assunto = "Senha alterada com sucesso";
        String mensagem = "Olá " + user.getName() + ",\n\n"
                + "Sua senha foi alterada com sucesso.\n\n"
                + "Se você não solicitou essa alteração, entre em contato conosco imediatamente.";

        emailService.enviarEmailTexto(user.getEmail(), assunto, mensagem);
    }

    @Transactional
    public void generatePasswordResetCode(String email) {
        // Verifica se o usuário existe
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o e-mail: " + email));

        //código numérico de 6 dígitos
        String resetCode = RandomStringUtils.randomNumeric(6);

        //expiração do código 15 minutos
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(15);

        // Salva o código e a data de expiração no banco
        user.setResetPasswordCode(resetCode);
        user.setCodeExpirationDate(expirationDate);
        userRepository.save(user);

        String assunto = "Recuperação de Senha - HardProject";
        String mensagem = "Olá " + user.getName() + ",\n\n"
                + "Você solicitou a recuperação de senha. Aqui está o seu código de verificação:\n\n"
                + "Código de recuperação: " + resetCode + "\n\n"
                + "O código é válido por 15 minutos.";

        emailService.enviarEmailTexto(user.getEmail(), assunto, mensagem);
    }

    @Transactional
    public void resetPassword(String email, String code, String newPassword) {
        // Verifica se o usuário existe
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o e-mail: " + email));

        // Verifica se o código de reset existe e é válido
        if (user.getResetPasswordCode() == null || !user.getResetPasswordCode().equals(code)) {
            throw new IllegalArgumentException("Código inválido.");
        }

        // Verifica se a data de expiração existe e não expirou
        if (user.getCodeExpirationDate() == null || user.getCodeExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Código expirado. Solicite novamente.");
        }

        // Atualiza a senha
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordCode(null); // Limpa o código após o uso
        user.setCodeExpirationDate(null); // Limpa a data de expiração
        userRepository.save(user);
    }
}


