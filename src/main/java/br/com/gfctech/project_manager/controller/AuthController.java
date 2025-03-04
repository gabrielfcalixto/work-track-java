package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gfctech.project_manager.dto.AuthDTO;
import br.com.gfctech.project_manager.dto.RegisterDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;  // Injeção do AuthenticationManager

    @Autowired
    private UserRepository userRepository;  // Injeção do repositório de usuários

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());  // Cria um token com as credenciais
        var auth = this.authenticationManager.authenticate(usernamePassword);  // Autentica o usuário com o AuthenticationManager

        return ResponseEntity.ok().build();  // Retorna resposta de sucesso
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();  // Retorna erro se o usuário já existe
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());  // Codifica a senha
        UserEntity newUser = new UserEntity(data.login(), encryptedPassword, data.role());  // Cria um novo usuário

        this.userRepository.save(newUser);  // Salva o usuário no repositório

        return ResponseEntity.ok().build();  // Retorna resposta de sucesso
    }
}
