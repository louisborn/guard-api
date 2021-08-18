package com.guard.restservice.tasks;

import com.guard.restservice.operator.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final OperatorService operatorService;

    @Autowired
    public TaskService(TaskRepository taskRepository, OperatorService operatorService) {
        this.taskRepository = taskRepository;
        this.operatorService = operatorService;
    }

    public List<Task> getTasks(String token) {
        if(!operatorService.checkTokenValidity(token)){
            throw new IllegalStateException("Access denied");
        }
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String token, long taskId) {
        if(!operatorService.checkTokenValidity(token)){
            throw new IllegalStateException("Access denied");
        }
        return taskRepository.findById(taskId);
    }

    public List<Task> getTasksByOperatorId(String token, long operatorId) {
        if(!operatorService.checkTokenValidity(token)){
            throw new IllegalStateException("Access denied");
        }
        return taskRepository.findTaskByOperatorId(operatorId);
    }

    public void deleteTaskById(String token, Long taskId) {
        if(!operatorService.checkTokenValidity(token)){
            throw new IllegalStateException("Access denied");
        }
        boolean exists = taskRepository.existsById(taskId);
        if(!exists){
            throw new IllegalStateException("Access denied");
        }
        taskRepository.deleteById(taskId);
    }

    @Transactional
    public void deleteAllCompletedTasks(String token) {
        if(!operatorService.checkTokenValidity(token)){
            throw new IllegalStateException("Access denied");
        }
        taskRepository.deleteAllCompletedTasks();
    }

    @Transactional
    public void updateTaskStatus(String token, long id) {
        try {
            Optional<Task> task = getTaskById(token, id);
            if(!task.isPresent()) {
                throw new IllegalStateException("Could not update operator");
            }
            Task task1 = task.get();
            task1.setIsCompleted(!task.get().getIsCompleted());
            taskRepository.save(task1);
        } catch(Exception e) {
            throw new IllegalStateException("Could not update task status");
        }
    }
}
