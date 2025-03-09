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

        // Verifica se já existe um usuário padrão "user"
        if (userRepository.findByLogin("user").isEmpty()) {
            // Cria o usuário padrão "user"
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

        // Script SQL para inserir dados iniciais
        String sql = """
            -- Inserir usuários com o e-mail 'gabrielfeifer18@gmail.com'
            INSERT INTO gfc_user (name, login, email, password, role, join_date)
            VALUES 
                ('Admin User', 'admine3', 'gabrielfeifer10@gmail.com', '$2a$10$exampleHashedPassword', 'ADMIN', NOW()),
                ('Gestor User', 'gestor', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
                ('User1', 'user1', 'gabrielfeifer21@gmail.com', '$2a$10$exampleHashedPassword', 'ADMIN', NOW()),
                ('User2', 'user2', 'gabrielcalixto0w@gmail.com', '$2a$10$exampleHashedPassword', 'ADMIN', NOW());

           -- Inserir clientes
                INSERT INTO GFC_CLIENTS (name, cnpj, razao_social, cpf, email, phone, address)
            VALUES
                ('Cliente 1', '26.608.044/0001-92', 'Ian e Miguel Doces & Salgados ME', '001.449.037-10', 'cliente1@example.com', '1234567890', 'Rua A, 123'),
                ('Cliente 2', '26.608.044/0001-93', 'Ian e Miguel Doces & Salgados', '001.449.037-12', 'cliente2@example.com', '0987654321', 'Avenida B, 456'),
                ('Cliente 3','26.608.044/0001-94', 'Ian e Miguel Doces & Salgados', '001.449.037-13', 'cliente3@example.com', '1122334455', 'Praça C, 789');



            -- Inserir projetos corretamente, agora com manager_id e client_id
            INSERT INTO gfc_projects (name, description, hours, status,  manager_id, client_id, start_date, deadline)
            VALUES 
                ('Project 1', 'Description of Project 1', 20, 'NOT_STARTED', 1, 1, CURRENT_DATE, CURRENT_DATE),
                ('Project 2', 'Description of Project 2', 20, 'NOT_STARTED', 2, 2, CURRENT_DATE, CURRENT_DATE),
                ('Project 3', 'Description of Project 3', 20, 'NOT_STARTED', 1, 2, CURRENT_DATE, CURRENT_DATE),
                ('Project 4', 'Description of Project 4', 20, 'NOT_STARTED', 2, 1, CURRENT_DATE, CURRENT_DATE);

            -- Inserir tasks corretamente e vincular à projetos
            INSERT INTO gfc_task (name, description, estimated_hours, status, total_hours, project_id, priority, start_date, deadline)
            VALUES 
                ('Task 1 for Project 1', 'Description of Task 1 for Project 1', 50, 'NOT_STARTED', 0.0, 1, 'LOW', CURRENT_DATE, CURRENT_DATE),
                ('Task 2 for Project 1', 'Description of Task 2 for Project 1', 50, 'NOT_STARTED', 0.0, 1, 'MEDIUM', CURRENT_DATE, CURRENT_DATE),
                ('Task 1 for Project 2', 'Description of Task 1 for Project 2', 50, 'NOT_STARTED', 0.0, 2, 'HIGH', CURRENT_DATE, CURRENT_DATE),
                ('Task 2 for Project 2', 'Description of Task 2 for Project 2', 50, 'NOT_STARTED', 0.0, 2, 'MEDIUM', CURRENT_DATE, CURRENT_DATE);

            -- Associar usuários às tasks (many-to-many)
            INSERT INTO gfc_task_user (task_id, user_id)
            VALUES 
                (1, 1),
                (2, 2),
                (3, 3),
                (4, 4);

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
