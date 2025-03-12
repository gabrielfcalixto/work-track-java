package br.com.gfctech.project_manager.service;

import br.com.gfctech.project_manager.dto.ProfileDTO;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.exceptions.UsuarioNaoEncontradoException;
import br.com.gfctech.project_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    public ProfileDTO getProfile(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + userId));

        return new ProfileDTO(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getRole().name(),
                user.getJoinDate()
        );
    }

    // public void updateProfile(Long userId, ProfileDTO profileDTO) {
    //     UserEntity user = userRepository.findById(userId)
    //             .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + userId));

    //     user.setName(profileDTO.getName());
    //     user.setEmail(profileDTO.getEmail());
    //     userRepository.save(user);
    // }
}