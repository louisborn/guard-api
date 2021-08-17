package com.guard.restservice.tasks;

import com.guard.restservice.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final OperatorService operatorService;

    @Autowired
    public TaskService(TaskRepository taskRepository, OperatorService operatorService) {
        this.taskRepository = taskRepository;
        this.operatorService = operatorService;
    }

    public List<Task> getTasksByOperatorId(String token, long operatorId) {
        boolean hasAccess = operatorService.getOperatorByToken(token);
        if(!hasAccess){
            throw new IllegalStateException("Access denied");
        }
        return taskRepository.findTaskByOperatorId(operatorId);
    }
}
