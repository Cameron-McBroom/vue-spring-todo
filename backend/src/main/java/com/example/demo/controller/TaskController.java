package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/task")
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }


    @PostMapping
    public Task addNewTask(@RequestBody Task task) {
        taskService.addTask(task);
        return task;
    }

    @DeleteMapping(path = "{id}")
    public Map<String, String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

        //check to see the task was deleted
        Optional<Task> optionalTask = taskService.getTaskById(id);

        if (optionalTask.isPresent()) {
            return Collections.singletonMap("success", "false");
        } else {
            return Collections.singletonMap("success", "true");
        }
    }

    @PostMapping(path = "{id}/update")
    public Optional<Task> updateTaskName(@PathVariable Long id, @RequestBody String name) {
        taskService.updateTask(id, name);
        return taskService.getTaskById(id);
    }

    @PutMapping(path = "{id}/{complete}")
    public Optional<Task> updateComplete(@PathVariable Long id, @PathVariable Boolean complete) {
        taskService.markTaskComplete(id, complete);
        return taskService.getTaskById(id);
    }


}

