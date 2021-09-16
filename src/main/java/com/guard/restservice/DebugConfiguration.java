package com.guard.restservice;

import com.guard.restservice.locations.Location;
import com.guard.restservice.locations.LocationRepository;
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
    CommandLineRunner commandLineRunner(OperatorRepository operatorRepository, TaskRepository taskRepository, NoteRepository noteRepository, LocationRepository locationRepository) {
        return args -> {
            //bramble-RQ3A.210805.001.A1
            Operator john = new Operator("John Williams", "john.williams@bosch.de", "abc123", "","", "");

            Task task1 = new Task(1, "Check B1" ,"Building B, ground floor", "10:32:22","2021-09-18",
                    "Please check B1 thoroughly once. Suspicion of intrusion.", true,true);
            Task task2 = new Task(1, "Patrol ground floor at 9:00am" ,"Building C, ground floor", "06:45:22","2021-09-18",
                    "-", false,false);
            Task task3 = new Task(1, "Support patrol at 9:30am" ,"B1, garage", "06:50:32","2021-09-18",
                    "Support Johnson at patrol as it is his first day.", false,false);
            Task task4 = new Task(1, "Test task no.: 4" ,"A2, top floor", "06:50:32","2021-09-18",
                    "Default description for test task", false,false);
            Task task5 = new Task(1, "Test task no.: 5" ,"A5, garage", "06:50:32","2021-09-18",
                    "Default description for test task", false,true);
            Task task6 = new Task(1, "Test task no.: 6" ,"C1, second floor, Room 5", "06:50:32","2021-09-18",
                    "Default description for test ask", false,false);

            Note note1 = new Note("New employee starts today", "-", "Andre Müller starts today. Only duo patrols.",
                    "John R. Williams", "05:00:45", "2021-09-18", false, true);

            Location location1 = new Location("A1", "Garage", "");
            Location location2 = new Location("A1", "Ground floor", "1");
            Location location3 = new Location("B1", "Garage", "");
            Location location4 = new Location("B1", "Ground floor", "2");
            Location location5 = new Location("B1", "Ground floor", "Meeting 2a");
            Location location6 = new Location("C1", "Second floor", "55c");
            Location location7 = new Location("No specific location", "", "");

            operatorRepository.save(john);

            taskRepository.save(task1);
            taskRepository.save(task2);
            taskRepository.save(task3);
            taskRepository.save(task4);
            taskRepository.save(task5);
            taskRepository.save(task6);

            noteRepository.save(note1);

            locationRepository.save(location1);
            locationRepository.save(location2);
            locationRepository.save(location3);
            locationRepository.save(location4);
            locationRepository.save(location5);
            locationRepository.save(location6);
            locationRepository.save(location7);

        };
    }
}
