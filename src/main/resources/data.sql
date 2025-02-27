-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (1, 'Jose', 'admin', 'admin123', 'admin@gfctech.com', 'ATIVO', 'ADMIN');
-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (2, 'Gabriel', 'gabriel', 'gabriel123', 'gabriel@gfctech.com', 'ATIVO', 'ADMIN');
-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (3, 'Ana', 'ana', 'ana123', 'ana@gfctech.com', 'ATIVO', 'MANAGER');
-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (4, 'Carlos', 'carlos', 'carlos123', 'carlos@gfctech.com', 'ATIVO', 'USER');
-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (5, 'Mariana', 'mariana', 'mariana123', 'mariana@gfctech.com', 'ATIVO', 'USER');
-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (6, 'Roberto', 'roberto', 'roberto123', 'roberto@gfctech.com', 'ATIVO', 'MANAGER');
-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (7, 'Lucas', 'lucas', 'lucas123', 'lucas@gfctech.com', 'ATIVO', 'USER');
-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (8, 'Juliana', 'juliana', 'juliana123', 'juliana@gfctech.com', 'ATIVO', 'MANAGER');
-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (9, 'Pedro', 'pedro', 'pedro123', 'pedro@gfctech.com', 'ATIVO', 'USER');
-- INSERT INTO GFC_USER (ID, NAME, LOGIN, PASSWORD, EMAIL, SITUACAO, ROLE) VALUES (10, 'Larissa', 'larissa', 'larissa123', 'larissa@gfctech.com', 'ATIVO', 'USER');


-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (1, 'Desenvolver API', 'Desenvolver a API para o sistema de gerenciamento de tarefas.', 10, 'Em andamento');
-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (2, 'Criar Interface', 'Criar a interface de usuário para o sistema.', 8, 'Pendente');
-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (3, 'Testar sistema', 'Realizar testes unitários e de integração.', 6, 'Pendente');
-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (4, 'Documentar código', 'Escrever a documentação do código desenvolvido.', 4, 'Pendente');
-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (5, 'Revisão de código', 'Revisar o código-fonte do sistema.', 5, 'Pendente');
-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (6, 'Configuração de banco de dados', 'Configurar o banco de dados para o sistema.', 7, 'Em andamento');
-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (7, 'Testes de performance', 'Realizar testes de performance no sistema.', 9, 'Pendente');
-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (8, 'Desenvolver relatórios', 'Desenvolver relatórios de análise de desempenho.', 6, 'Pendente');
-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (9, 'Refatorar código', 'Refatorar o código para melhorar a performance.', 3, 'Pendente');
-- INSERT INTO GFC_TASK (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (10, 'Treinamento de equipe', 'Realizar treinamento da equipe sobre o sistema.', 5, 'Pendente');


-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (1, 'Implementar autenticação JWT', 'Desenvolver e integrar autenticação segura no sistema.', 12, 'Concluído');
-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (2, 'Criar dashboard de métricas', 'Desenvolver painel para visualizar métricas de uso.', 15, 'Em andamento');
-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (3, 'Implementar CI/CD', 'Configurar pipeline de integração e entrega contínua.', 10, 'Pendente');
-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (4, 'Revisão de arquitetura', 'Avaliar e otimizar a arquitetura do sistema.', 8, 'Pendente');
-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (5, 'Correção de bugs críticos', 'Identificar e corrigir falhas reportadas pelos usuários.', 6, 'Em andamento');
-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (6, 'Otimização de banco de dados', 'Melhorar a performance e escalabilidade do banco.', 9, 'Concluído');
-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (7, 'Criar testes automatizados', 'Implementar testes unitários e de integração.', 14, 'Em andamento');
-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (8, 'Atualizar documentação', 'Revisar e expandir a documentação do sistema.', 5, 'Pendente');
-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (9, 'Desenvolver API pública', 'Criar uma API acessível para integração externa.', 20, 'Pendente');
-- INSERT INTO GFC_PROJECTS (ID, NAME, DESCRIPTION, HOURS, STATUS) VALUES (10, 'Treinamento sobre novas tecnologias', 'Capacitar a equipe em novas ferramentas e frameworks.', 7, 'Em andamento');

-- INSERT INTO GFC_TIMEENTRY (ID, USER_ID, TASK_ID, DESCRIPTION, ENTRY_DATE, START_TIME, END_TIME, TOTAL_HOURS) VALUES (1, 1, 1, 'Desenvolvimento da API RESTful', CURRENT_DATE, '08:00', '12:00', 4);
-- INSERT INTO GFC_TIMEENTRY (ID, USER_ID, TASK_ID, DESCRIPTION, ENTRY_DATE, START_TIME, END_TIME, TOTAL_HOURS) VALUES (2, 2, 2, 'Criação de interfaces responsivas', CURRENT_DATE, '13:00', '17:30', 4.5);
-- INSERT INTO GFC_TIMEENTRY (ID, USER_ID, TASK_ID, DESCRIPTION, ENTRY_DATE, START_TIME, END_TIME, TOTAL_HOURS) VALUES (3, 3, 3, 'Desenvolvimento de testes unitários', CURRENT_DATE, '09:30', '11:30', 2);

-- assigned_user_id

-- project_id

-- Inserir usuários com o e-mail 'gabrielfeifer18@gmail.com'
INSERT INTO gfc_user (id, name, login, email, password, situacao, role)
VALUES 
    (1, 'Admin User', 'admin', 'gabrielfeifer18@gmail.com', NULL, 'PENDENTE','ADMIN'),
    (2, 'Gestor User', 'gestor', 'gabrielfeifer18@gmail.com', NULL, 'PENDENTE','USER'),
    (3, 'User1', 'user1', 'gabrielfeifer18@gmail.com', NULL, 'PENDENTE','ADMIN'),
    (4, 'User2', 'user2', 'gabrielfeifer18@gmail.com', NULL, 'PENDENTE','ADMIN');

-- Inserir verificação de usuário com UUID (gerando manualmente os UUIDs para os exemplos)
INSERT INTO GFC_USER_VERIFICATION (id, user_id, uuid, date_expiration)
VALUES 
    (1, 1, 'e1b5fa0f-1f93-47b7-b1e2-24e01d16a6a9', CURRENT_TIMESTAMP + INTERVAL '15 minutes'),
    (2, 2, '7f1e9d1d-d7a4-4c5c-b999-9e1c8d38f49b', CURRENT_TIMESTAMP + INTERVAL '15 minutes'),
    (3, 3, 'f9a0db58-e8ad-4563-93d7-d7ae57a9c6fa', CURRENT_TIMESTAMP + INTERVAL '15 minutes'),
    (4, 4, 'cd29be39-1799-4317-8d7d-e258d9f3b32c', CURRENT_TIMESTAMP + INTERVAL '15 minutes');

-- Inserir projetos
INSERT INTO gfc_projects (ID, NAME, DESCRIPTION, HOURS, STATUS)
VALUES 
    (1, 'Project 1', 'Description of Project 1', 20, 'pendente'),
    (2, 'Project 2', 'Description of Project 2', 20, 'pendente');

-- Inserir tasks e vincular a usuários e projetos
INSERT INTO gfc_task (ID, NAME, DESCRIPTION, HOURS, STATUS, project_id, assigned_user_id)
VALUES 
    (1, 'Task 1 for Project 1', 'Description of Task 1 for Project 1', 50,'PENDENTE', 1, 1),
    (2, 'Task 2 for Project 1', 'Description of Task 2 for Project 1', 50,'PENDENTE', 1, 2),
    (3, 'Task 1 for Project 2', 'Description of Task 1 for Project 2', 50,'PENDENTE', 2, 3),
    (4, 'Task 2 for Project 2', 'Description of Task 2 for Project 2', 50,'PENDENTE', 2, 4);


INSERT INTO GFC_TIMEENTRY (ID, USER_ID, TASK_ID, DESCRIPTION, ENTRY_DATE, START_TIME, END_TIME, TOTAL_HOURS) VALUES (1, 1, 1, 'Desenvolvimento da API RESTful', CURRENT_DATE, '08:00', '12:00', 4);
INSERT INTO GFC_TIMEENTRY (ID, USER_ID, TASK_ID, DESCRIPTION, ENTRY_DATE, START_TIME, END_TIME, TOTAL_HOURS) VALUES (2, 2, 2, 'Criação de interfaces responsivas', CURRENT_DATE, '13:00', '17:30', 4.5);
INSERT INTO GFC_TIMEENTRY (ID, USER_ID, TASK_ID, DESCRIPTION, ENTRY_DATE, START_TIME, END_TIME, TOTAL_HOURS) VALUES (3, 3, 3, 'Desenvolvimento de testes unitários', CURRENT_DATE, '09:30', '11:30', 2);