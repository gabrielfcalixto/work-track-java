package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gfctech.project_manager.dto.ChangePasswordRequest;
import br.com.gfctech.project_manager.dto.UserDTO;
import br.com.gfctech.project_manager.service.UserService;

import java.util.HashMap;
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

    @PostMapping("/add")
    public void addUser(@RequestBody UserDTO addUser) {
        userService.addUser(addUser);
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

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@PathVariable Long id,
                                                               @RequestBody ChangePasswordRequest changePasswordRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.changePassword(id, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
            response.put("message", "Senha alterada com sucesso!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}