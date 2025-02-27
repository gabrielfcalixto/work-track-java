package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.gfctech.project_manager.dto.AuthenticationDTO;
import br.com.gfctech.project_manager.dto.UserDTO;
import br.com.gfctech.project_manager.service.AuthService;
import br.com.gfctech.project_manager.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto) {
		return ResponseEntity.ok(authService.login(authDto));
		
		
	}
	@PostMapping(value = "/addUser")
	public void addUser(@RequestBody UserDTO addUser){
		userService.addUser(addUser);
		
	}
	@GetMapping(value="/verificarCadastro/{uuid}")
	public String verificarCadastro(@PathVariable("uuid") String uuid) {
		return userService.verificarCadastro(uuid);
	}

}
