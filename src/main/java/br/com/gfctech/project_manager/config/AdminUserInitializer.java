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
        -- Inserir usuários com e-mails diferentes
        INSERT INTO gfc_user (name, login, email, password, role, join_date)
        VALUES 
            ('Usuário Admin', 'admine3', 'gabrielfeifer10@gmail.com', '$2a$10$exampleHashedPassword', 'ADMIN', NOW()),
            ('Usuário Gestor', 'gestor', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Usuário1', 'user1', 'gabrielfeifer21@gmail.com', '$2a$10$exampleHashedPassword', 'ADMIN', NOW()),
            ('Usuário2', 'user2', 'gabrielcalixto0w@gmail.com', '$2a$10$exampleHashedPassword', 'ADMIN', NOW()),
            ('Usuário3', 'user3', 'maria.johnson@gmail.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Usuário4', 'user4', 'jose.silva@example.com', '$2a$10$exampleHashedPassword', 'MANAGER', NOW()),
            ('Usuário5', 'user5', 'lucas.martins@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW());

        -- Inserir clientes com informações detalhadas
        INSERT INTO GFC_CLIENTS (name, cnpj, razao_social, cpf, email, phone, address)
        VALUES
            ('Cliente 1', '26.608.044/0001-92', 'Ian e Miguel Doces & Salgados ME', '001.449.037-10', 'cliente1@example.com', '1234567890', 'Rua A, 123'),
            ('Cliente 2', '26.608.044/0001-93', 'Ian e Miguel Doces & Salgados', '001.449.037-12', 'cliente2@example.com', '0987654321', 'Avenida B, 456'),
            ('Cliente 3','26.608.044/0001-94', 'Ian e Miguel Doces & Salgados', '001.449.037-13', 'cliente3@example.com', '1122334455', 'Praça C, 789'),
            ('Cliente 4', '45.678.987/0001-00', 'Tech Innovations', '987.654.321-00', 'cliente4@example.com', '1122334455', 'Rua D, 101'),
            ('Cliente 5', '55.567.987/0001-11', 'Digital Solutions Inc.', '123.456.789-01', 'cliente5@example.com', '3344556677', 'Avenida E, 202');

        -- Inserir projetos com gestores e clientes
        INSERT INTO gfc_projects (name, description, hours, status, manager_id, client_id, start_date, deadline)
        VALUES 
            ('Projeto 1', 'Descrição do Projeto 1', 20, 'NAO_INICIADO', 1, 1, CURRENT_DATE, CURRENT_DATE),
            ('Projeto 2', 'Descrição do Projeto 2', 20, 'NAO_INICIADO', 2, 2, CURRENT_DATE, CURRENT_DATE),
            ('Projeto 3', 'Descrição do Projeto 3', 20, 'NAO_INICIADO', 1, 2, CURRENT_DATE, CURRENT_DATE),
            ('Projeto 4', 'Descrição do Projeto 4', 20, 'NAO_INICIADO', 2, 1, CURRENT_DATE, CURRENT_DATE),
            ('Projeto 5', 'Descrição do Projeto 5', 30, 'NAO_INICIADO', 4, 3, CURRENT_DATE, CURRENT_DATE + INTERVAL '10 days'),
            ('Projeto 6', 'Descrição do Projeto 6', 40, 'NAO_INICIADO', 3, 4, CURRENT_DATE, CURRENT_DATE + INTERVAL '15 days');

        -- Inserir tarefas com vinculação a projetos e detalhes
        INSERT INTO gfc_task (name, description, estimated_hours, status, total_hours, project_id, priority, start_date, deadline)
        VALUES 
            ('Tarefa 1 para o Projeto 1', 'Descrição da Tarefa 1 para o Projeto 1', 50, 'NAO_INICIADA', 0.0, 1, 'BAIXA', CURRENT_DATE, CURRENT_DATE),
            ('Tarefa 2 para o Projeto 1', 'Descrição da Tarefa 2 para o Projeto 1', 50, 'NAO_INICIADA', 0.0, 1, 'MEDIA', CURRENT_DATE, CURRENT_DATE),
            ('Tarefa 1 para o Projeto 2', 'Descrição da Tarefa 1 para o Projeto 2', 50, 'NAO_INICIADA', 0.0, 2, 'ALTA', CURRENT_DATE, CURRENT_DATE),
            ('Tarefa 2 para o Projeto 2', 'Descrição da Tarefa 2 para o Projeto 2', 50, 'NAO_INICIADA', 0.0, 2, 'MEDIA', CURRENT_DATE, CURRENT_DATE),
            ('Tarefa 1 para o Projeto 3', 'Descrição da Tarefa 1 para o Projeto 3', 40, 'NAO_INICIADA', 10.0, 3, 'BAIXA', CURRENT_DATE, CURRENT_DATE + INTERVAL '5 days'),
            ('Tarefa 2 para o Projeto 4', 'Descrição da Tarefa 2 para o Projeto 4', 30, 'NAO_INICIADA', 0.0, 4, 'ALTA', CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days'),
            ('Tarefa 3 para o Projeto 5', 'Descrição da Tarefa 3 para o Projeto 5', 60, 'NAO_INICIADA', 0.0, 5, 'ALTA', CURRENT_DATE, CURRENT_DATE + INTERVAL '10 days');

        -- Associar usuários às tarefas
        INSERT INTO gfc_task_user (task_id, user_id)
        VALUES 
            (1, 1),
            (2, 2),
            (3, 3),
            (4, 4),
            (5, 5),
            (6, 6),
            (7, 1);

        -- Inserir registros de tempo
        INSERT INTO gfc_time_entry (user_id, task_id, description, entry_date, start_time, end_time, total_hours)
        VALUES 
            (1, 1, 'Desenvolvimento da API RESTful', CURRENT_DATE, '08:00', '12:00', 4),
            (2, 2, 'Criação de interfaces responsivas', CURRENT_DATE, '13:00', '17:30', 4.5),
            (3, 3, 'Desenvolvimento de testes unitários', CURRENT_DATE, '09:30', '11:30', 2),
            (4, 4, 'Planejamento de projeto', CURRENT_DATE, '10:00', '12:30', 2.5),
            (5, 5, 'Revisão de código', CURRENT_DATE, '14:00', '18:00', 4),
            (6, 6, 'Análise de requisitos', CURRENT_DATE, '09:00', '13:00', 4),
            (1, 7, 'Desenvolvimento de documentação', CURRENT_DATE, '15:00', '17:00', 2);
            """;

        // Executa o script SQL
        jdbcTemplate.execute(sql);
        System.out.println("Dados iniciais inseridos com sucesso!");
    }
}
