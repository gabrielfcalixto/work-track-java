-- Inserir usuários com o e-mail 'gabrielfeifer18@gmail.com'
INSERT INTO gfc_user (name, login, email, password, situacao, role)
VALUES 
    ('Admin User', 'admine3', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'PENDENTE', 'ADMIN'),
    ('Gestor User', 'gestor', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'PENDENTE', 'USER'),
    ('User1', 'user1', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'PENDENTE', 'ADMIN'),
    ('User2', 'user2', 'gabrielfeifer18@gmail.com', '$2a$10$exampleHashedPassword', 'PENDENTE', 'ADMIN');

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