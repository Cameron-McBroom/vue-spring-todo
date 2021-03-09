package com.example.demo.config;

import com.example.demo.model.Student;
import com.example.demo.model.Task;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ModelTestConfig {

    StudentRepository studentRepository;
    TaskRepository taskRepository;

    @Autowired
    public ModelTestConfig(StudentRepository studentRepository, TaskRepository taskRepository) {
        this.studentRepository = studentRepository;
        this.taskRepository = taskRepository;
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {

            // Some test students
            Student cameron = new Student(
                    "cameron mcbroom",
                    "cam.mcbroom@gmail.com",
                    LocalDate.of(1997, 8, 20)
            );

            Student john = new Student(
                    "john smith",
                    "john.smith@gmail.com",
                    LocalDate.of(1994, 6, 15)
            );

            studentRepository.saveAll(List.of(cameron, john));

            // Some test tasks
            Task task1 = new Task("Feed the dog");
            Task task2 = new Task("Do the shopping");

            taskRepository.saveAll(List.of(task1, task2));
        };
    }
}
