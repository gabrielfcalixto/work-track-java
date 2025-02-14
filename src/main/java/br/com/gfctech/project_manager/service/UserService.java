package br.com.gfctech.project_manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gfctech.project_manager.dto.UserDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.repository.UserRepository;

@Service
public class UserService {

    @Autowired  
    private UserRepository userRepository;

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
        existingUser.setRole(userDTO.getRole());
        // Atualize outros campos conforme necessário
        return new UserDTO(userRepository.save(existingUser));
    }

    @Transactional
    public UserDTO updatePermissions(Long id, String role) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        user.setRole(role);
        return new UserDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        userRepository.delete(user);
    }
}
