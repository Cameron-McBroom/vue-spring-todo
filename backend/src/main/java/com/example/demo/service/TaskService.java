package com.example.demo.service;


import com.example.demo.model.Student;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }


    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) throws EntityNotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isEmpty()) {
            throw new EntityNotFoundException("Task id not found: " + taskId);
        }

        taskRepository.deleteById(taskId);
    }

    @Transactional
    public void updateTask(Long id, String name) {
        // Check to see if the task exists
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task id not found: " + id));

        // Check the name is not null or the same
        if (name != null && name.length() > 0 && !(task.getName().equals(name))) {
            task.setName(name);
        }
    }

    @Transactional
    public void markTaskComplete(Long id, Boolean complete) {
        // Check to see if the task exists
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task id not found: " + id));

        // Mark task as complete otherwise
        task.setComplete(complete);
    }

}
