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
    UserEntity user1 = new UserEntity();
    user1.setName("Jose");
    user1.setLogin("admin");
    user1.setPassword("admin123");
    user1.setEmail("admin@gfctech.com");
    user1.setRole("admin");

    UserEntity user2 = new UserEntity();
    user2.setName("Gabriel");
    user2.setLogin("gabriel");
    user2.setPassword("gabriel123");
    user2.setEmail("gabriel@gfctech.com");
    user2.setRole("user");

    UserEntity user3 = new UserEntity();
    user3.setName("Ana");
    user3.setLogin("ana");
    user3.setPassword("ana123");
    user3.setEmail("ana@gfctech.com");
    user3.setRole("user");

    UserEntity user4 = new UserEntity();
    user4.setName("Carlos");
    user4.setLogin("carlos");
    user4.setPassword("carlos123");
    user4.setEmail("carlos@gfctech.com");
    user4.setRole("user");

    UserEntity user5 = new UserEntity();
    user5.setName("Mariana");
    user5.setLogin("mariana");
    user5.setPassword("mariana123");
    user5.setEmail("mariana@gfctech.com");
    user5.setRole("user");

    UserEntity user6 = new UserEntity();
    user6.setName("Roberto");
    user6.setLogin("roberto");
    user6.setPassword("roberto123");
    user6.setEmail("roberto@gfctech.com");
    user6.setRole("user");

    userRepository.saveAll(List.of(user1, user2, user3, user4, user5, user6));

    System.out.println("Usuários padrão criados!");
}
}
