package br.com.gfctech.project_manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.entity.UserEntity.Role;
import br.com.gfctech.project_manager.entity.enums.TipoSituacaoUsuario;
import br.com.gfctech.project_manager.repository.UserRepository;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe um usuário admin
        if (userRepository.findByLogin("admin").isEmpty()) {
            // Cria o usuário admin padrão
            UserEntity adminUser = new UserEntity();
            adminUser.setName("Administrador");
            adminUser.setLogin("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Senha padrão
            adminUser.setRole(Role.ADMIN);
            adminUser.setSituacao(TipoSituacaoUsuario.ATIVO);

            // Salva o usuário admin no banco de dados
            userRepository.save(adminUser);

            System.out.println("Usuário admin criado com sucesso!");
        } else {
            System.out.println("Usuário admin já existe.");
        }
    }
}