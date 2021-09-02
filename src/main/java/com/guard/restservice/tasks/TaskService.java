package com.guard.restservice.tasks;

import com.guard.restservice.operator.Operator;
import com.guard.restservice.operator.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<Task> getTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<Task> getTaskById(long taskId) {
        try {
            return taskRepository.findById(taskId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Task> getTasksByOperatorId(long operatorId) {
        try {
            return taskRepository.findTaskByOperatorId(operatorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteAllCompletedTasks(String token) {
        try {
            Optional<Operator> operator = operatorService.getOperatorByToken(token);
            if(operator.isPresent()) {
                long id = operator.get().getId();
                taskRepository.deleteAllCompletedTasks(id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteTaskById(Long taskId) {
        try {
            boolean exists = taskRepository.existsById(taskId);
            if(!exists){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            taskRepository.deleteById(taskId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void updateTaskStatus(long id) {
        try {
            Optional<Task> task = getTaskById(id);
            if(!task.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            Task task1 = task.get();
            task1.setIsCompleted(!task.get().getIsCompleted());
            taskRepository.save(task1);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
