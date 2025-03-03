package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gfctech.project_manager.dto.AcessDTO;
import br.com.gfctech.project_manager.dto.AuthenticationDTO;
import br.com.gfctech.project_manager.dto.UserDTO;
import br.com.gfctech.project_manager.secury.jwt.JwtUtils;
import br.com.gfctech.project_manager.service.AuthService;
import br.com.gfctech.project_manager.service.UserDetailsImpl;
import br.com.gfctech.project_manager.service.UserService;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;




    @PostMapping("/login") // Corrigido o endpoint para evitar duplicação "/auth/auth/login"
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto) {
        try {
            // Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPassword())
            );
    
            // Gera o token JWT
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateTokenFromUserDetailsImpl(userDetails);
    
            // Retorna o token na resposta
            return ResponseEntity.ok(new AcessDTO(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
    

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserDTO addUser) {
        userService.addUser(addUser);
    }

    @PostMapping("/definir-senha")
    public ResponseEntity<String> definirSenha(@RequestParam String token, @RequestParam String newPassword) {
        String mensagem = userService.definirSenha(token, newPassword);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/verificarCadastro/{uuid}")
    public String verificarCadastro(@PathVariable String uuid) {
        return userService.verificarCadastro(uuid);
    }
}