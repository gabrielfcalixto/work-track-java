package br.com.gfctech.project_manager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.entity.ProjectEntity;
import br.com.gfctech.project_manager.entity.TaskEntity;
import br.com.gfctech.project_manager.entity.TimeEntryEntity;
import br.com.gfctech.project_manager.repository.UserRepository;
import br.com.gfctech.project_manager.repository.TaskRepository;
import br.com.gfctech.project_manager.repository.TimeEntryRepository;
import br.com.gfctech.project_manager.repository.ProjectRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TimeEntryRepository timeEntryRepository;


    public DataInitializer(UserRepository userRepository, TaskRepository taskRepository, ProjectRepository projectRepository, TimeEntryRepository timeEntryRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.timeEntryRepository = timeEntryRepository;
    }

    @Override
    public void run(String... args) {
        // Criando usuários
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

        UserEntity user7 = new UserEntity();
        user7.setName("Lucas");
        user7.setLogin("lucas");
        user7.setPassword("lucas123");
        user7.setEmail("lucas@gfctech.com");
        user7.setRole("user");

        UserEntity user8 = new UserEntity();
        user8.setName("Juliana");
        user8.setLogin("juliana");
        user8.setPassword("juliana123");
        user8.setEmail("juliana@gfctech.com");
        user8.setRole("user");

        UserEntity user9 = new UserEntity();
        user9.setName("Pedro");
        user9.setLogin("pedro");
        user9.setPassword("pedro123");
        user9.setEmail("pedro@gfctech.com");
        user9.setRole("user");

        UserEntity user10 = new UserEntity();
        user10.setName("Larissa");
        user10.setLogin("larissa");
        user10.setPassword("larissa123");
        user10.setEmail("larissa@gfctech.com");
        user10.setRole("user");

        // Salvando usuários
        userRepository.saveAll(List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10));
        System.out.println("Usuários padrão criados!");

        // Criando tarefas
        TaskEntity task1 = new TaskEntity();
        task1.setName("Desenvolver API");
        task1.setDescription("Desenvolver a API para o sistema de gerenciamento de tarefas.");
        task1.setHours(10f);
        task1.setStatus("Em andamento");

        TaskEntity task2 = new TaskEntity();
        task2.setName("Criar Interface");
        task2.setDescription("Criar a interface de usuário para o sistema.");
        task2.setHours(8f);
        task2.setStatus("Pendente");

        TaskEntity task3 = new TaskEntity();
        task3.setName("Testar sistema");
        task3.setDescription("Realizar testes unitários e de integração.");
        task3.setHours(6f);
        task3.setStatus("Pendente");

        TaskEntity task4 = new TaskEntity();
        task4.setName("Documentar código");
        task4.setDescription("Escrever a documentação do código desenvolvido.");
        task4.setHours(4f);
        task4.setStatus("Pendente");

        TaskEntity task5 = new TaskEntity();
        task5.setName("Revisão de código");
        task5.setDescription("Revisar o código-fonte do sistema.");
        task5.setHours(5f);
        task5.setStatus("Pendente");

        TaskEntity task6 = new TaskEntity();
        task6.setName("Configuração de banco de dados");
        task6.setDescription("Configurar o banco de dados para o sistema.");
        task6.setHours(7f);
        task6.setStatus("Em andamento");

        TaskEntity task7 = new TaskEntity();
        task7.setName("Testes de performance");
        task7.setDescription("Realizar testes de performance no sistema.");
        task7.setHours(9f);
        task7.setStatus("Pendente");

        TaskEntity task8 = new TaskEntity();
        task8.setName("Desenvolver relatórios");
        task8.setDescription("Desenvolver relatórios de análise de desempenho.");
        task8.setHours(6f);
        task8.setStatus("Pendente");

        TaskEntity task9 = new TaskEntity();
        task9.setName("Refatorar código");
        task9.setDescription("Refatorar o código para melhorar a performance.");
        task9.setHours(3f);
        task9.setStatus("Pendente");

        TaskEntity task10 = new TaskEntity();
        task10.setName("Treinamento de equipe");
        task10.setDescription("Realizar treinamento da equipe sobre o sistema.");
        task10.setHours(5f);
        task10.setStatus("Pendente");

        // Salvando tarefas
        taskRepository.saveAll(List.of(task1, task2, task3, task4, task5, task6, task7, task8, task9, task10));
        System.out.println("Tarefas padrão criadas!");



        // Criando projetos
        ProjectEntity project1 = new ProjectEntity();
        project1.setName("Implementar autenticação JWT");
        project1.setDescription("Desenvolver e integrar autenticação segura no sistema.");
        project1.setHours(12f);
        project1.setStatus("Concluído");

        ProjectEntity project2 = new ProjectEntity();
        project2.setName("Criar dashboard de métricas");
        project2.setDescription("Desenvolver painel para visualizar métricas de uso.");
        project2.setHours(15f);
        project2.setStatus("Em andamento");

        ProjectEntity project3 = new ProjectEntity();
        project3.setName("Implementar CI/CD");
        project3.setDescription("Configurar pipeline de integração e entrega contínua.");
        project3.setHours(10f);
        project3.setStatus("Pendente");

        ProjectEntity project4 = new ProjectEntity();
        project4.setName("Revisão de arquitetura");
        project4.setDescription("Avaliar e otimizar a arquitetura do sistema.");
        project4.setHours(8f);
        project4.setStatus("Pendente");

        ProjectEntity project5 = new ProjectEntity();
        project5.setName("Correção de bugs críticos");
        project5.setDescription("Identificar e corrigir falhas reportadas pelos usuários.");
        project5.setHours(6f);
        project5.setStatus("Em andamento");

        ProjectEntity project6 = new ProjectEntity();
        project6.setName("Otimização de banco de dados");
        project6.setDescription("Melhorar a performance e escalabilidade do banco.");
        project6.setHours(9f);
        project6.setStatus("Concluído");

        ProjectEntity project7 = new ProjectEntity();
        project7.setName("Criar testes automatizados");
        project7.setDescription("Implementar testes unitários e de integração.");
        project7.setHours(14f);
        project7.setStatus("Em andamento");

        ProjectEntity project8 = new ProjectEntity();
        project8.setName("Atualizar documentação");
        project8.setDescription("Revisar e expandir a documentação do sistema.");
        project8.setHours(5f);
        project8.setStatus("Pendente");

        ProjectEntity project9 = new ProjectEntity();
        project9.setName("Desenvolver API pública");
        project9.setDescription("Criar uma API acessível para integração externa.");
        project9.setHours(20f);
        project9.setStatus("Pendente");

        ProjectEntity project10 = new ProjectEntity();
        project10.setName("Treinamento sobre novas tecnologias");
        project10.setDescription("Capacitar a equipe em novas ferramentas e frameworks.");
        project10.setHours(7f);
        project10.setStatus("Em andamento");

        // Salvando tarefas
        projectRepository.saveAll(List.of(project1, project2, project3, project4, project5, project6, project7, project8, project9, project10));
        System.out.println("Projetos padrão criadas!");

      List<UserEntity> users = userRepository.findAll();
        List<TaskEntity> tasks = taskRepository.findAll();
        
        if (users.isEmpty() || tasks.isEmpty()) {
            System.out.println("Não há usuários ou tarefas suficientes para criar lançamentos de horas.");
            return;
        }

        // ----- Inicialização dos lançamentos de horas (Time Entries) ----- //

        // Lançamento 1: usuário 1 na tarefa 1, das 08:00 às 12:00 do dia atual
        TimeEntryEntity entry1 = new TimeEntryEntity();
        entry1.setTaskEntity(tasks.get(0)); // primeira tarefa
        entry1.setUserEntity(users.get(0)); // primeiro usuário
        entry1.setDescription("Desenvolvimento da API RESTful");
        entry1.setEntryDate(LocalDate.now());
        entry1.setStartTime(LocalTime.of(8, 0));
        entry1.setEndTime(LocalTime.of(12, 0));
        entry1.calculateTotalHours();

        // Lançamento 2: usuário 2 na tarefa 2, das 13:00 às 17:30 do dia atual
        TimeEntryEntity entry2 = new TimeEntryEntity();
        entry2.setTaskEntity(tasks.size() > 1 ? tasks.get(1) : tasks.get(0)); // segunda tarefa ou a primeira se não existir
        entry2.setUserEntity(users.size() > 1 ? users.get(1) : users.get(0)); // segundo usuário ou o primeiro se não existir
        entry2.setDescription("Criação de interfaces responsivas");
        entry2.setEntryDate(LocalDate.now());
        entry2.setStartTime(LocalTime.of(13, 0));
        entry2.setEndTime(LocalTime.of(17, 30));
        entry2.calculateTotalHours();

        // Lançamento 3: usuário 3 na tarefa 3, das 09:30 às 11:30 do dia atual
        TimeEntryEntity entry3 = new TimeEntryEntity();
        entry3.setTaskEntity(tasks.size() > 2 ? tasks.get(2) : tasks.get(0)); // terceira tarefa ou a primeira se não existir
        entry3.setUserEntity(users.size() > 2 ? users.get(2) : users.get(0)); // terceiro usuário ou o primeiro se não existir
        entry3.setDescription("Desenvolvimento de testes unitários");
        entry3.setEntryDate(LocalDate.now());
        entry3.setStartTime(LocalTime.of(9, 30));
        entry3.setEndTime(LocalTime.of(11, 30));
        entry3.calculateTotalHours();

        // Salvando os lançamentos
        timeEntryRepository.saveAll(List.of(entry1, entry2, entry3));
        System.out.println("Lançamentos de horas padrão criados!");
    }

}
