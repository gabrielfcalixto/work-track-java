package br.com.gfctech.project_manager.config;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.entity.UserEntity.Role;
import br.com.gfctech.project_manager.repository.UserRepository;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            adminUser.setName("Gabriel Calixto");
            adminUser.setLogin("admin");
            adminUser.setEmail("gabrielfeiferc@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Senha padrão
            adminUser.setRole(Role.ADMIN);
            adminUser.setJoinDate(LocalDate.now()); // Define a data de cadastro

            // Salva o usuário admin no banco de dados
            userRepository.save(adminUser);

            System.out.println("Usuário admin criado com sucesso!");
        } else {
            System.out.println("Usuário admin já existe.");
        }

                // Verifica se já existe um usuário admin
                if (userRepository.findByLogin("manager").isEmpty()) {
                    // Cria o usuário admin padrão
                    UserEntity managerUser = new UserEntity();
                    managerUser.setName("M Gabriel");
                    managerUser.setLogin("manager");
                    managerUser.setEmail("versospintando@gmail.com");
                    managerUser.setPassword(passwordEncoder.encode("manager123")); // Senha padrão
                    managerUser.setRole(Role.MANAGER);
                    managerUser.setJoinDate(LocalDate.now()); // Define a data de cadastro
        
                    // Salva o usuário admin no banco de dados
                    userRepository.save(managerUser);
        
                    System.out.println("Usuário manager criado com sucesso!");
                } else {
                    System.out.println("Usuário manager já existe.");
                }
        // Verifica se já existe um usuário padrão "user"
        if (userRepository.findByLogin("user").isEmpty()) {
            UserEntity defaultUser = new UserEntity();
            defaultUser.setName("Usuário Padrão");
            defaultUser.setLogin("user");
            defaultUser.setEmail("user@example.com");
            defaultUser.setPassword(passwordEncoder.encode("user123")); // Senha padrão
            defaultUser.setRole(Role.USER);
            defaultUser.setJoinDate(LocalDate.now()); // Define a data de cadastro

            // Salva o usuário padrão "user" no banco de dados
            userRepository.save(defaultUser);

            System.out.println("Usuário padrão 'user' criado com sucesso!");
        } else {
            System.out.println("Usuário padrão 'user' já existe.");
        }
        // Executa o script SQL
        System.out.println("Dados iniciais inseridos com sucesso!");
    }
}
