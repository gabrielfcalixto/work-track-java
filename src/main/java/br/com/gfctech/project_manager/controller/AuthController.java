package br.com.gfctech.project_manager.controller;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import br.com.gfctech.project_manager.dto.AcessDTO;
import br.com.gfctech.project_manager.dto.AuthenticationDTO;
import br.com.gfctech.project_manager.dto.ResetPasswordDTO;
import br.com.gfctech.project_manager.exceptions.UsuarioNaoEncontradoException;
import br.com.gfctech.project_manager.secury.jwt.JwtUtils;
import br.com.gfctech.project_manager.service.UserDetailsImpl;
import br.com.gfctech.project_manager.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200") // Permite requisições do frontend
@Tag(
    name = "Autenticação", 
    description = "Endpoints responsáveis pela autenticação de usuários. Utilize o endpoint **auth/login** para obter um token de acesso. Exemplo de credenciais: **username:** admin, **password:** admin123. exemplo de request:{\r\n" + //
                "  \"password\": \"admin\",\r\n" + //
                "  \"username\": \"admin123\"\r\n" + //
                "} "
)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody AuthenticationDTO authDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPassword())
            );
    
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateTokenFromUserDetailsImpl(userDetails);
    
            return ResponseEntity.ok(new AcessDTO(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("status", "error", "message", "Credenciais inválidas")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of("status", "error", "message", "Erro interno no servidor")
            );
        }
    }

    
    @PostMapping("/generate-reset-code")
    public ResponseEntity<String> generateResetCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            userService.generatePasswordResetCode(email);
            return ResponseEntity.ok().build();
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao gerar o código de reset: " + e.getMessage());
        }
  
  }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Validated @RequestBody ResetPasswordDTO request) {
        try {
            String email = request.getEmail();
            String code = request.getCode();
            String newPassword = request.getNewPassword();

            userService.resetPassword(email, code, newPassword);
            return ResponseEntity.ok().build();
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao redefinir a senha: " + e.getMessage());
        }
    }
}