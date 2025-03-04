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
            adminUser.setName("Administrador");
            adminUser.setLogin("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Senha padrão
            adminUser.setRole(Role.ADMIN);
            adminUser.setJoinDate(LocalDate.now()); // Define a data de cadastro

            // Salva o usuário admin no banco de dados
            userRepository.save(adminUser);

            System.out.println("Usuário admin criado com sucesso!");
        } else {
            System.out.println("Usuário admin já existe.");
        }

        // Script SQL para inserir dados iniciais
        String sql = """
            -- Inserir usuários com o e-mail 'gabrielfeifer18@gmail.com'
            INSERT INTO gfc_user (name, login, email, password, situacao, role, join_date)
            VALUES 
                ('Admin User', 'admine3', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'PENDENTE', 'ADMIN', NOW()),
                ('Gestor User', 'gestor', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'PENDENTE', 'USER', NOW()),
                ('User1', 'user1', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'PENDENTE', 'ADMIN', NOW()),
                ('User2', 'user2', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'PENDENTE', 'ADMIN', NOW());

            -- Inserir verificação de usuário com UUID (gerando manualmente os UUIDs para os exemplos)
            INSERT INTO gfc_user_verification (user_id, uuid, date_expiration)
            VALUES 
                (1, 'e1b5fa0f-1f93-47b7-b1e2-24e01d16a6a9', CURRENT_TIMESTAMP + INTERVAL '15 minutes'),
                (2, '7f1e9d1d-d7a4-4c5c-b999-9e1c8d38f49b', CURRENT_TIMESTAMP + INTERVAL '15 minutes'),
                (3, 'f9a0db58-e8ad-4563-93d7-d7ae57a9c6fa', CURRENT_TIMESTAMP + INTERVAL '15 minutes'),
                (4, 'cd29be39-1799-4317-8d7d-e258d9f3b32c', CURRENT_TIMESTAMP + INTERVAL '15 minutes');

            -- Inserir projetos
            INSERT INTO gfc_projects (name, description, hours, status)
            VALUES 
                ('Project 1', 'Description of Project 1', 20, 'pendente'),
                ('Project 2', 'Description of Project 2', 20, 'pendente');

            -- Inserir tasks e vincular a usuários e projetos
            INSERT INTO gfc_task (name, description, hours, status, project_id, assigned_user_id)
            VALUES 
                ('Task 1 for Project 1', 'Description of Task 1 for Project 1', 50, 'PENDENTE', 1, 1),
                ('Task 2 for Project 1', 'Description of Task 2 for Project 1', 50, 'PENDENTE', 1, 2),
                ('Task 1 for Project 2', 'Description of Task 1 for Project 2', 50, 'PENDENTE', 2, 3),
                ('Task 2 for Project 2', 'Description of Task 2 for Project 2', 50, 'PENDENTE', 2, 4);

            -- Inserir registros de tempo
            INSERT INTO gfc_timeentry (user_id, task_id, description, entry_date, start_time, end_time, total_hours)
            VALUES 
                (1, 1, 'Desenvolvimento da API RESTful', CURRENT_DATE, '08:00', '12:00', 4),
                (2, 2, 'Criação de interfaces responsivas', CURRENT_DATE, '13:00', '17:30', 4.5),
                (3, 3, 'Desenvolvimento de testes unitários', CURRENT_DATE, '09:30', '11:30', 2);
            """;

        // Executa o script SQL
        jdbcTemplate.execute(sql);
        System.out.println("Dados iniciais inseridos com sucesso!");
    }
}