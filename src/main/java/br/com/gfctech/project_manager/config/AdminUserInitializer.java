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
        -- USUARIOS
        INSERT INTO gfc_user (name, login, email, password, role, join_date)
        VALUES 
            ('Gabriel Feifer', 'gabriel.feifer', 'gabrielfeifer10@gmail.com', '$2a$10$exampleHashedPassword', 'ADMIN', NOW()),
            ('Maria Silva', 'maria.silva', 'maria.silva@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Lucas Martins', 'lucas.martins', 'lucas.martins@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Ana Costa', 'ana.costa', 'ana.costa@example.com', '$2a$10$exampleHashedPassword', 'MANAGER', NOW()),
            ('João Souza', 'joao.souza', 'joao.souza@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Roberta Oliveira', 'roberta.oliveira', 'roberta.oliveira@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Pedro Almeida', 'pedro.almeida', 'pedro.almeida@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Fernanda Lima', 'fernanda.lima', 'fernanda.lima@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Carlos Pereira', 'carlos.pereira', 'carlos.pereira@example.com', '$2a$10$exampleHashedPassword', 'MANAGER', NOW()),
            ('Paula Ramos', 'paula.ramos', 'paula.ramos@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Bruna Costa', 'bruna.costa', 'bruna.costa@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Ricardo Alves', 'ricardo.alves', 'ricardo.alves@example.com', '$2a$10$exampleHashedPassword', 'ADMIN', NOW()),
            ('Luciana Martins', 'luciana.martins', 'luciana.martins@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Gustavo Souza', 'gustavo.souza', 'gustavo.souza@example.com', '$2a$10$exampleHashedPassword', 'MANAGER', NOW()),
            ('Isabela Oliveira', 'isabela.oliveira', 'isabela.oliveira@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Felipe Costa', 'felipe.costa', 'felipe.costa@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Clara Lima', 'clara.lima', 'clara.lima@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Eduardo Pereira', 'eduardo.pereira', 'eduardo.pereira@example.com', '$2a$10$exampleHashedPassword', 'ADMIN', NOW()),
            ('Renata Santos', 'renata.santos', 'renata.santos@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW()),
            ('Matheus Rodrigues', 'matheus.rodrigues', 'matheus.rodrigues@example.com', '$2a$10$exampleHashedPassword', 'USER', NOW());


       -- Inserir clientes com informações detalhadas
        INSERT INTO GFC_CLIENTS (name, cnpj, razao_social, cpf, email, phone, address)
        VALUES
            ('Tech Solutions', '12.345.678/0001-90', 'Tech Solutions Ltda', '123.456.789-01', 'techsolutions@example.com', '11987654321', 'Rua do Progresso, 100'),
            ('Marketing Experts', '98.765.432/0001-00', 'Marketing Experts SA', '987.654.321-00', 'contact@marketingexperts.com', '1122334455', 'Avenida do Marketing, 200'),
            ('Design Studio', '22.334.455/0001-00', 'Design Studio Ltda', '223.334.455-66', 'info@designstudio.com', '11999887766', 'Rua do Design, 300'),
            ('Innovative Tech', '11.223.344/0001-11', 'Innovative Tech Corp', '112.223.334-99', 'support@innotech.com', '1177665544', 'Rua das Inovações, 400'),
            ('Educação Online', '44.556.677/0001-22', 'Educação Online Ltda', '445.667.788-10', 'educacaoonline@example.com', '11966554433', 'Avenida da Educação, 500'),
            ('Creative Labs', '77.888.999/0001-33', 'Creative Labs Ltda', '778.899.000-22', 'contact@creativelabs.com', '11987766554', 'Rua da Criatividade, 600'),
            ('Global Services', '33.445.556/0001-44', 'Global Services Ltda', '334.455.667-88', 'service@globalservices.com', '1122336677', 'Avenida Global, 700'),
            ('Digital Experts', '66.777.888/0001-55', 'Digital Experts SA', '667.788.999-00', 'info@digitalexperts.com', '1198877665', 'Rua Digital, 800'),
            ('Health Plus', '99.000.111/0001-66', 'Health Plus Ltda', '990.001.122-33', 'contact@healthplus.com', '1199888777', 'Avenida Saúde, 900'),
            ('Green Innovations', '55.666.777/0001-77', 'Green Innovations Ltda', '556.667.788-99', 'info@greeninnovations.com', '1197766554', 'Rua Verde, 1000'),
            ('Techno Solutions', '88.999.000/0001-88', 'Techno Solutions Ltda', '889.900.111-00', 'tech@technosolutions.com', '1195554433', 'Avenida Tecnologia, 1100'),
            ('Foodie Co.', '44.221.333/0001-22', 'Foodie Co. Ltda', '442.213.334-55', 'contact@foodieco.com', '1198776655', 'Rua da Alimentação, 1200'),
            ('Smart Devices', '22.110.330/0001-00', 'Smart Devices SA', '221.103.334-77', 'info@smartdevices.com', '1199988665', 'Avenida Inteligente, 1300'),
            ('Adventures Travel', '33.444.555/0001-99', 'Adventures Travel Ltda', '334.445.556-88', 'support@adventurestravel.com', '1199998777', 'Rua da Aventura, 1400'),
            ('Luxury Homes', '77.888.999/0001-11', 'Luxury Homes Ltda', '778.899.111-00', 'contact@luxuryhomes.com', '1196677889', 'Avenida Luxo, 1500'),
            ('Smart Tech Solutions', '11.223.344/0001-22', 'Smart Tech Solutions Ltda', '112.223.445-55', 'info@smarttech.com', '1196677888', 'Rua da Tecnologia, 1600'),
            ('Fitness Solutions', '44.556.677/0001-33', 'Fitness Solutions Ltda', '445.667.788-77', 'contact@fitnesssolutions.com', '1195544332', 'Rua Saúde, 1700'),
            ('EduTech', '66.777.888/0001-99', 'EduTech Ltda', '667.788.999-11', 'support@edutech.com', '1195566777', 'Avenida Educação, 1800'),
            ('Media Group', '55.666.777/0001-88', 'Media Group SA', '556.667.789-22', 'contact@mediagroup.com', '1199988776', 'Rua da Mídia, 1900'),
            ('Logistics Experts', '22.334.445/0001-55', 'Logistics Experts Ltda', '223.344.455-77', 'info@logistics.com', '1197766553', 'Avenida Logística, 2000');


        -- Inserir projetos com gestores e clientes
        INSERT INTO gfc_projects (name, description, hours, status, manager_id, client_id, start_date, deadline)
        VALUES 
            ('Desenvolvimento de Software de Gestão', 'Projeto para desenvolver um software de gestão para pequenas empresas, com módulos financeiros, de estoque e vendas.', 300, 'NAO_INICIADO', 1, 1, CURRENT_DATE, CURRENT_DATE + INTERVAL '120 days'),
            ('Reformulação do Website Institucional', 'Recriação e modernização do site institucional da empresa, com foco na experiência do usuário e no design responsivo.', 150, 'NAO_INICIADO', 2, 2, CURRENT_DATE, CURRENT_DATE + INTERVAL '45 days'),
            ('Desenvolvimento de Aplicativo Móvel', 'Criação de um aplicativo móvel para Android e iOS, voltado para o público jovem, com funcionalidades de social media e e-commerce.', 250, 'NAO_INICIADO', 3, 3, CURRENT_DATE, CURRENT_DATE + INTERVAL '75 days'),
            ('Criação de Plataforma E-learning', 'Desenvolvimento de plataforma online para cursos de ensino à distância, com integração de vídeos e certificação digital.', 180, 'NAO_INICIADO', 4, 4, CURRENT_DATE, CURRENT_DATE + INTERVAL '60 days'),
            ('Sistema de Gestão de Recursos Humanos', 'Desenvolvimento de um sistema de RH para gerenciar contratações, folha de pagamento e benefícios.', 200, 'NAO_INICIADO', 5, 5, CURRENT_DATE, CURRENT_DATE + INTERVAL '90 days'),
            ('Desenvolvimento de CRM Personalizado', 'Criação de um sistema de CRM personalizado para melhorar o relacionamento com os clientes, incluindo gerenciamento de contatos e oportunidades de vendas.', 220, 'NAO_INICIADO', 6, 6, CURRENT_DATE, CURRENT_DATE + INTERVAL '105 days'),
            ('Plataforma de Vendas Online', 'Desenvolvimento de uma plataforma de e-commerce personalizada, com integração a gateways de pagamento e sistemas de estoque.', 250, 'NAO_INICIADO', 7, 7, CURRENT_DATE, CURRENT_DATE + INTERVAL '90 days'),
            ('Reforma de Identidade Visual e Branding', 'Recriação da identidade visual da empresa, incluindo logo, paleta de cores, tipografia e materiais gráficos.', 80, 'NAO_INICIADO', 8, 8, CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days'),
            ('Otimização de SEO para Website', 'Implementação de técnicas de SEO para aumentar o tráfego orgânico e melhorar o ranqueamento do site nos motores de busca.', 60, 'NAO_INICIADO', 9, 9, CURRENT_DATE, CURRENT_DATE + INTERVAL '40 days'),
            ('Campanha de Marketing nas Redes Sociais', 'Criação e gerenciamento de campanhas de marketing digital nas principais plataformas sociais, como Facebook, Instagram e LinkedIn.', 100, 'NAO_INICIADO', 10, 10, CURRENT_DATE, CURRENT_DATE + INTERVAL '50 days'),
            ('Desenvolvimento de Plataforma de Análise de Dados', 'Criação de uma plataforma de business intelligence para análise de dados de vendas e clientes, com dashboards interativos e relatórios personalizados.', 160, 'NAO_INICIADO', 11, 11, CURRENT_DATE, CURRENT_DATE + INTERVAL '120 days'),
            ('Integração de Chatbot com IA', 'Desenvolvimento e integração de um chatbot inteligente para atendimento ao cliente no site da empresa.', 90, 'NAO_INICIADO', 12, 12, CURRENT_DATE, CURRENT_DATE + INTERVAL '40 days'),
            ('Desenvolvimento de Sistema ERP', 'Criação de um sistema ERP (Enterprise Resource Planning) para integração de processos financeiros, contábeis e de logística.', 300, 'NAO_INICIADO', 13, 13, CURRENT_DATE, CURRENT_DATE + INTERVAL '180 days'),
            ('Migração para Nuvem e Backup', 'Migração de servidores físicos para a nuvem, garantindo escalabilidade e segurança dos dados com sistemas de backup eficientes.', 150, 'NAO_INICIADO', 14, 14, CURRENT_DATE, CURRENT_DATE + INTERVAL '100 days'),
            ('Plataforma de Comunicação Corporativa', 'Criação de uma plataforma interna de comunicação para empresas, com ferramentas de mensagens instantâneas, videoconferência e compartilhamento de documentos.', 200, 'NAO_INICIADO', 15, 15, CURRENT_DATE, CURRENT_DATE + INTERVAL '110 days'),
            ('Sistema de Automação de Marketing', 'Desenvolvimento de uma plataforma de automação de marketing para segmentação de leads, envio de e-mails personalizados e análise de resultados.', 180, 'NAO_INICIADO', 16, 16, CURRENT_DATE, CURRENT_DATE + INTERVAL '90 days'),
            ('Plataforma de Feedback do Cliente', 'Criação de uma plataforma online para coletar feedbacks de clientes e gerar relatórios analíticos para a melhoria contínua dos produtos.', 120, 'NAO_INICIADO', 17, 17, CURRENT_DATE, CURRENT_DATE + INTERVAL '80 days');


        -- Inserir tarefas com vinculação a projetos e detalhes
            INSERT INTO gfc_task (name, description, estimated_hours, status, total_hours, project_id, priority, start_date, deadline)
            VALUES 
                ('Desenvolvimento de Módulo Financeiro', 'Desenvolvimento do módulo de controle financeiro do software, com funcionalidades de contas a pagar e a receber.', 80, 'NAO_INICIADA', 0.0, 1, 'ALTA', CURRENT_DATE, CURRENT_DATE + INTERVAL '15 days'),
                ('Implementação de API de Pagamento', 'Implementação de uma API de pagamento para integração com sistemas bancários, permitindo transações seguras e rápidas.', 60, 'NAO_INICIADA', 0.0, 1, 'ALTA', CURRENT_DATE, CURRENT_DATE + INTERVAL '10 days'),
                ('Desenvolvimento da Página de Produtos', 'Criação da página de exibição de produtos na plataforma de e-commerce, com filtros de categorias e preços.', 70, 'NAO_INICIADA', 0.0, 2, 'MEDIA', CURRENT_DATE, CURRENT_DATE + INTERVAL '12 days'),
                ('Integração de Sistema de Carrinho', 'Integração do sistema de carrinho de compras com o backend para garantir uma experiência de compra sem falhas.', 50, 'NAO_INICIADA', 0.0, 2, 'ALTA', CURRENT_DATE, CURRENT_DATE + INTERVAL '10 days'),
                ('Desenvolvimento de Sistema de Cadastro de Usuários', 'Desenvolvimento de funcionalidade de cadastro de novos usuários e gerenciamento de perfil dentro do sistema.', 40, 'NAO_INICIADA', 0.0, 3, 'MEDIA', CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days'),
                ('Ajustes de Responsividade', 'Ajustes no layout do site para garantir que seja responsivo e acessível em diferentes dispositivos, como smartphones e tablets.', 30, 'NAO_INICIADA', 0.0, 4, 'BAIXA', CURRENT_DATE, CURRENT_DATE + INTERVAL '8 days'),
                ('Implementação de Funcionalidade de Login', 'Criação de sistema de login com autenticação via e-mail e senha para os usuários acessarem suas contas.', 60, 'NAO_INICIADA', 0.0, 5, 'MEDIA', CURRENT_DATE, CURRENT_DATE + INTERVAL '10 days'),
                ('Desenvolvimento de Dashboard do Admin', 'Criação do painel administrativo do sistema, com funcionalidades de visualização de vendas, usuários e configurações.', 120, 'NAO_INICIADA', 0.0, 6, 'ALTA', CURRENT_DATE, CURRENT_DATE + INTERVAL '20 days'),
                ('Teste de Sistema e Debug', 'Realização de testes no sistema para identificar e corrigir bugs e falhas na funcionalidade de pagamentos e envio de e-mails.', 50, 'NAO_INICIADA', 0.0, 7, 'ALTA', CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days'),
                ('Otimização do Sistema para SEO', 'Ajustes nas tags HTML, URL e conteúdo do site para melhorar o ranqueamento nos motores de busca e otimizar a experiência do usuário.', 40, 'NAO_INICIADA', 0.0, 8, 'MEDIA', CURRENT_DATE, CURRENT_DATE + INTERVAL '6 days'),
                ('Criação de Material Promocional', 'Desenvolvimento de banners, anúncios e outros materiais promocionais para a campanha de lançamento do e-commerce.', 30, 'NAO_INICIADA', 0.0, 9, 'BAIXA', CURRENT_DATE, CURRENT_DATE + INTERVAL '4 days'),
                ('Desenvolvimento de Funcionalidade de Comentários', 'Implementação de sistema de comentários nos produtos da plataforma de e-commerce, permitindo interação dos usuários.', 50, 'NAO_INICIADA', 0.0, 10, 'MEDIA', CURRENT_DATE, CURRENT_DATE + INTERVAL '8 days'),
                ('Integração de Ferramenta de Análise de Dados', 'Integração de ferramenta de análise de dados para gerar relatórios e insights sobre as vendas e comportamento dos usuários.', 80, 'NAO_INICIADA', 0.0, 11, 'ALTA', CURRENT_DATE, CURRENT_DATE + INTERVAL '14 days'),
                ('Desenvolvimento de Funcionalidade de Feedback', 'Criação de sistema para coleta de feedback dos usuários sobre a experiência de compra e navegação no site.', 60, 'NAO_INICIADA', 0.0, 12, 'MEDIA', CURRENT_DATE, CURRENT_DATE + INTERVAL '10 days'),
                ('Criação de Página de Confirmação de Compra', 'Desenvolvimento da página de confirmação após o processo de compra, detalhando informações de envio e pagamento.', 50, 'NAO_INICIADA', 0.0, 13, 'BAIXA', CURRENT_DATE, CURRENT_DATE + INTERVAL '5 days'),
                ('Implementação de Sistema de Notificações', 'Implementação de sistema de notificações push para alertar os usuários sobre novos produtos e promoções.', 60, 'NAO_INICIADA', 0.0, 14, 'MEDIA', CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days'),
                ('Teste de Funcionalidade de Carrinho de Compras', 'Testar a funcionalidade do carrinho de compras, incluindo a adição de itens, modificação de quantidade e cálculo de preços finais.', 40, 'NAO_INICIADA', 0.0, 15, 'ALTA', CURRENT_DATE, CURRENT_DATE + INTERVAL '6 days'),
                ('Desenvolvimento de Sistema de Pagamento por Boleto', 'Criação de sistema de pagamento por boleto bancário para permitir pagamento de compras no site.', 50, 'NAO_INICIADA', 0.0, 16, 'MEDIA', CURRENT_DATE, CURRENT_DATE + INTERVAL '8 days'),
                ('Ajustes de Interface de Usuário (UI)', 'Ajustes no design de interface do usuário para tornar o site mais intuitivo e fácil de navegar.', 40, 'NAO_INICIADA', 0.0, 17, 'BAIXA', CURRENT_DATE, CURRENT_DATE + INTERVAL '5 days');


                -- Associar usuários às tarefas
                INSERT INTO gfc_task_user (task_id, user_id)
                VALUES 
                    (1, 1),  -- Tarefa 1 para o Projeto 1 com Gabriel Feifer
                    (2, 2),  -- Tarefa 2 para o Projeto 1 com Maria Silva
                    (3, 3),  -- Tarefa 1 para o Projeto 2 com Lucas Martins
                    (4, 4),  -- Tarefa 2 para o Projeto 2 com Ana Costa
                    (5, 5),  -- Tarefa 1 para o Projeto 3 com João Souza
                    (6, 6),  -- Tarefa 2 para o Projeto 4 com Roberta Oliveira
                    (7, 1),  -- Tarefa 3 para o Projeto 5 com Gabriel Feifer
                    (8, 2),  -- Tarefa 4 para o Projeto 1 com Maria Silva
                    (9, 3),  -- Tarefa 5 para o Projeto 2 com Lucas Martins
                    (10, 4), -- Tarefa 6 para o Projeto 3 com Ana Costa
                    (11, 5), -- Tarefa 7 para o Projeto 4 com João Souza
                    (12, 6), -- Tarefa 8 para o Projeto 5 com Roberta Oliveira
                    (13, 1), -- Tarefa 9 para o Projeto 6 com Gabriel Feifer
                    (14, 2), -- Tarefa 10 para o Projeto 6 com Maria Silva
                    (15, 3), -- Tarefa 11 para o Projeto 7 com Lucas Martins
                    (16, 4), -- Tarefa 12 para o Projeto 8 com Ana Costa
                    (17, 5); -- Tarefa 13 para o Projeto 9 com João Souza

        -- Inserir registros de tempo
        INSERT INTO gfc_time_entry (user_id, task_id, description, entry_date, start_time, end_time, total_hours)
        VALUES 
            (1, 1, 'Desenvolvimento da API RESTful', CURRENT_DATE, '08:00', '12:00', 4),
            (2, 2, 'Criação de interfaces responsivas', CURRENT_DATE, '13:00', '17:30', 4.5),
            (3, 3, 'Desenvolvimento de testes unitários', CURRENT_DATE, '09:30', '11:30', 2),
            (4, 4, 'Planejamento de projeto', CURRENT_DATE, '10:00', '12:30', 2.5),
            (5, 5, 'Revisão de código', CURRENT_DATE, '14:00', '18:00', 4),
            (6, 6, 'Análise de requisitos', CURRENT_DATE, '09:00', '13:00', 4),
            (1, 7, 'Desenvolvimento de documentação', CURRENT_DATE, '15:00', '17:00', 2),
            (2, 8, 'Desenvolvimento de tela de login', CURRENT_DATE, '08:00', '12:00', 4),
            (3, 9, 'Testes de usabilidade', CURRENT_DATE, '13:00', '16:30', 3.5),
            (4, 10, 'Definição de escopo', CURRENT_DATE, '08:30', '12:00', 3.5),
            (5, 11, 'Correção de bugs', CURRENT_DATE, '09:00', '13:00', 4),
            (6, 12, 'Documentação de requisitos', CURRENT_DATE, '14:00', '17:30', 3.5),
            (1, 13, 'Desenvolvimento de módulo de API', CURRENT_DATE, '08:00', '12:30', 4.5),
            (2, 14, 'Design de página principal', CURRENT_DATE, '13:00', '17:00', 4),
            (3, 15, 'Testes de integração', CURRENT_DATE, '09:00', '11:30', 2.5),
            (4, 16, 'Planejamento de integração de sistemas', CURRENT_DATE, '10:00', '13:00', 3),
            (5, 17, 'Ajustes finais no layout', CURRENT_DATE, '14:00', '18:00', 4);




            """;

        // Executa o script SQL
        jdbcTemplate.execute(sql);
        System.out.println("Dados iniciais inseridos com sucesso!");
    }
}
