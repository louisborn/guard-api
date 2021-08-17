package com.guard.restservice.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "{token}/tasks/{operatorId}")
    public List<Task> getTasks(
            @PathVariable("token") String token,
            @PathVariable("operatorId") Long id) {
        return taskService.getTasksByOperatorId(token, id);
    }


}
