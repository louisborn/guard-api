package com.guard.restservice.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Returns all available tasks. Independent of the operator id.
    @GetMapping(path = "{token}/tasks")
    public List<Task> getTasks(
            @PathVariable("token") String token
    ) {
        return taskService.getTasks(token);
    }

    // Returns only the tasks assigned to the operator id.
    @GetMapping(path = "{token}/tasks/{operatorId}")
    public List<Task> getTasksByOperatorId(
            @PathVariable("token") String token,
            @PathVariable("operatorId") Long id
    ) {
        return taskService.getTasksByOperatorId(token, id);
    }

    // Deletes a single task by its id.
    @DeleteMapping(path = "{token}/tasks/delete/{taskId}")
    public Map<String, Boolean> deleteTaskById(
            @PathVariable("token") String token,
            @PathVariable("taskId") Long id
    ) {
        taskService.deleteTaskById(token, id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    // Deletes all completed tasks.
    @DeleteMapping(path = "{token}/tasks/delete")
    public Map<String, Boolean> deleteAllCompletedTasks(
            @PathVariable("token") String token
    ) {
        taskService.deleteAllCompletedTasks(token);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    // Toggles the task completion status. Initial state: false.
    @PutMapping(path = "{token}/tasks/{taskId}/update")
    public Map<String, Boolean> updateTaskStatus(
            @PathVariable("token") String token,
            @PathVariable("taskId") long id
    ) {
        taskService.updateTaskStatus(token, id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return response;
    }
}
