package com.guard.restservice;

import com.guard.restservice.notes.Note;
import com.guard.restservice.notes.NoteRepository;
import com.guard.restservice.operator.Operator;
import com.guard.restservice.operator.OperatorRepository;
import com.guard.restservice.tasks.Task;
import com.guard.restservice.tasks.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class DebugConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(OperatorRepository operatorRepository, TaskRepository taskRepository, NoteRepository noteRepository) {
        return args -> {
            Operator john = new Operator("John Williams", "john.williams@bosch.de", "abc123", "bramble-RQ3A.210805.001.A1","ZXhhbXBsZS1kZXZpY2UtaWR+ZXhhbXBsZS1hcHAtaWR+am9obi53aWxsaWFtc0Bib3NjaC5kZX4yMDIxLTA4LTIw", "example-app-id");

            Task task1 = new Task(1, "Check B1" ,"Building B, ground floor", "10:32:22","2021-09-18",
                    "Please check B1 thoroughly once. Suspicion of intrusion.", true,true);
            Task task2 = new Task(1, "Patrol ground floor at 9:00am" ,"Building C, ground floor", "06:45:22","2021-09-18",
                    "-", false,false);
            Task task3 = new Task(1, "Support patrol at 9:30am" ,"B1, garage", "06:50:32","2021-09-18",
                    "Support Johnson at patrol as it is his first day.", false,false);

            Note note1 = new Note("New employee starts today", "-", "Andre Müller starts today. Only duo patrols.",
                    "John R. Williams", "05:00:45", "2021-09-18", false, true);

            operatorRepository.save(john);

            taskRepository.save(task1);
            taskRepository.save(task2);
            taskRepository.save(task3);

            noteRepository.save(note1);
        };
    }
}