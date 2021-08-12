package com.guard.restservice.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks(String token) {
        boolean exists = taskRepository.findTaskByToken(token).isPresent();
        if (!exists) {
            throw new IllegalStateException("access denied");
        }
        return taskRepository.findAll();
    }
}
