package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gfctech.project_manager.dto.UserDTO;
import br.com.gfctech.project_manager.entity.UserEntity.Role;
import br.com.gfctech.project_manager.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "http://localhost:4200") // Permite requisições do frontend
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserDTO addUser, Role role) {
        userService.addUser(addUser, role);
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


    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    


    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {  
        return userService.updateUser(id, userDTO);
    }

    @PatchMapping("/{id}/permissions")
    public UserDTO updatePermissions(@PathVariable Long id, @RequestBody Map<String, String> update) {
        return userService.updatePermissions(id, update.get("role"));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }



}