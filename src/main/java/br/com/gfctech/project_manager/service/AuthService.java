package br.com.gfctech.project_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity login(String login, String password) {
        // Busca o usuário pelo email
        UserEntity user = userRepository.findByLogin(login);
        // Verifica se o usuário existe e se a senha confere
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
