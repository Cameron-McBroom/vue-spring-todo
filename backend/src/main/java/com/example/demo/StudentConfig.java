package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {

    StudentRepository studentRepository;

    @Autowired
    public StudentConfig(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student cameron = new Student(
                    1L,
                    "cameron mcbroom",
                    "cam.mcbroom@gmail.com",
                    LocalDate.of(1997, 8, 20)
            );

            Student john = new Student(
                    2L,
                    "john smith",
                    "john.smith@gmail.com",
                    LocalDate.of(1994, 6, 15)
            );

            studentRepository.saveAll(List.of(cameron, john));


        };
    }
}
