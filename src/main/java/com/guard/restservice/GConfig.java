package com.guard.restservice;

import com.guard.restservice.tasks.Task;
import com.guard.restservice.tasks.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GConfig {

    @Bean
    CommandLineRunner commandLineRunner(OperatorRepository operatorRepository, TaskRepository taskRepository) {
        return args -> {
            Operator john = new Operator("John Williams", "john.williams@bosch.de", "abc123", "");

            Task task1 = new Task(1, "Check B1" ,"Building B, ground floor", "10:32:22","2021-09-18",
                    "Please check B1 thoroughly once. Suspicion of intrusion.", true,true);
            Task task2 = new Task(1, "Check B1" ,"Building B, ground floor", "10:32:22","2021-09-18",
                    "Please check B1 thoroughly once. Suspicion of intrusion.", false,true);

            operatorRepository.saveAll(List.of(john));

            taskRepository.saveAll(List.of(task1, task2));
        };
    }
}
