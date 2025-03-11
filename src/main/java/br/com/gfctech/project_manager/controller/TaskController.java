package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.gfctech.project_manager.dto.TaskDTO;
import br.com.gfctech.project_manager.entity.TaskEntity;
import br.com.gfctech.project_manager.enums.TaskStatus;
import br.com.gfctech.project_manager.service.TaskService;
import br.com.gfctech.project_manager.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/task")
@CrossOrigin(origins = "http://localhost:4200") // Permite requisições do frontend
public class TaskController {

    @Autowired
    private TaskService taskService;

    
        // Construtor para injeção de dependência
    public TaskController(TaskService taskService) {
            this.taskService = taskService;
        }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/usertask/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
    @PostMapping("/add")
    public TaskDTO addTask(@RequestBody TaskDTO taskDTO) {  
        return taskService.addTask(taskDTO);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {  
        return taskService.updateTask(id, taskDTO);
    }

   @PatchMapping("/update-status/{id}")
    public TaskDTO updateStatus(@PathVariable Long id, @RequestBody Map<String, String> update) {
        TaskStatus newStatus;
        try {
            newStatus = TaskStatus.valueOf(update.get("status").toUpperCase()); // Converte para maiúsculas para garantir a correspondência correta
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + update.get("status"));
        }
        return taskService.updateTaskStatus(id, newStatus);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    
}