package br.com.gfctech.project_manager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import br.com.gfctech.project_manager.entity.UserEntity;
import br.com.gfctech.project_manager.entity.TaskEntity;
import br.com.gfctech.project_manager.repository.UserRepository;
import br.com.gfctech.project_manager.repository.TaskRepository;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public DataInitializer(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
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
    }
}
