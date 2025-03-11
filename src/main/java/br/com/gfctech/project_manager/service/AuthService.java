package br.com.gfctech.project_manager.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.gfctech.project_manager.dto.AcessDTO;
import br.com.gfctech.project_manager.dto.AuthenticationDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.repository.UserRepository;
import br.com.gfctech.project_manager.secury.jwt.JwtUtils;
import jakarta.transaction.Transactional;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticatioManager;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepository userRepository;  // Injetando o repositório para salvar o usuário atualizado

	@Transactional
	public AcessDTO login(AuthenticationDTO authDto) {
		
		try {
		//Cria mecanismo de credencial para o spring
		UsernamePasswordAuthenticationToken userAuth = 
				new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPassword());
		
		//Prepara mecanismo para autenticacao
		Authentication authentication = authenticatioManager.authenticate(userAuth);
		
		//Busca usuario logado
		UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();

        UserEntity user = userAuthenticate.getUser();  
		user.setUltimoLogin(LocalDateTime.now());  
		userRepository.save(user);  
		System.out.println("Ultimo Login Atualizado: " + user.getUltimoLogin());


		String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
		
		AcessDTO accessDto = new AcessDTO(token);
		
		return accessDto;
		
		} catch (BadCredentialsException e) {
			// Caso as credenciais estejam erradas
			return new AcessDTO("Credenciais inválidas");
		} catch (Exception e) {
			// Erro interno
			return new AcessDTO("Erro interno no servidor");
		}
	}

	public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();  // Obtém o usuário autenticado
    }
}