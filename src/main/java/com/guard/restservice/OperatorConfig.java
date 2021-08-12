package com.guard.restservice;

import com.guard.restservice.tasks.Task;
import com.guard.restservice.tasks.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OperatorConfig {

    @Bean
    CommandLineRunner commandLineRunner(OperatorRepository operatorRepository, TaskRepository taskRepository) {
        return args -> {
            Operator john = new Operator("John", "john.williams@bosch.de", "abc123");

            Task task1 = new Task("Check B1", "Building B, ground floor", "ex12345",true);

            operatorRepository.saveAll(List.of(john));

            taskRepository.saveAll(List.of(task1));
        };
    }
}
