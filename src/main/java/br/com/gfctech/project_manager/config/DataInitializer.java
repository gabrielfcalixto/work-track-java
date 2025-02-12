package br.com.gfctech.project_manager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.repository.UserRepository;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            UserEntity user1 = new UserEntity();
            user1.setName("Admin");
            user1.setLogin("admin");
            user1.setPassword("admin123"); // Idealmente, use um hash para a senha
            user1.setEmail("admin@gfctech.com");

            UserEntity user2 = new UserEntity();
            user2.setName("Gabriel");
            user2.setLogin("gabriel");
            user2.setPassword("gabriel123"); // Também deve ser criptografada
            user2.setEmail("gabriel@gfctech.com");

            userRepository.saveAll(List.of(user1, user2));

            System.out.println("Usuários padrão criados!");
        } else {
            System.out.println("Usuários já existem. Nenhuma ação necessária.");
        }
    }
}
