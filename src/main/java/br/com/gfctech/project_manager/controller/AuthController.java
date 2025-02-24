package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gfctech.project_manager.dto.AuthDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.service.AuthService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO, HttpSession session) {
        UserEntity user = authService.login(authDTO.getLogin(), authDTO.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
        // Armazena o usuário na sessão para autenticação simples
        session.setAttribute("user", user);
        return ResponseEntity.ok(user);
    }
}
