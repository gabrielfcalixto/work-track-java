package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.gfctech.project_manager.dto.TaskDTO;
import br.com.gfctech.project_manager.service.TaskService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/task")
@CrossOrigin(origins = "http://localhost:4200") // Permite requisições do frontend
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public TaskDTO addTask(@RequestBody TaskDTO taskDTO) {  
        return taskService.addTask(taskDTO);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {  
        return taskService.updateTask(id, taskDTO);
    }

    @PatchMapping("/{id}/status")
    public TaskDTO updateStatus(@PathVariable Long id, @RequestBody Map<String, String> update) {
        return taskService.updateStatus(id, update.get("status"));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}