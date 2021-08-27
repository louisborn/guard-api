package com.guard.restservice.tasks;

import com.guard.restservice.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    /** The response map after a request */
    private final Map<String, Boolean> response = new HashMap<>();

    private final TaskService taskService;

    /** Used to validate the unique operator token
     *  included in the api requests' header.
     *  If the token validation fails, the request is denied.
     */
    private final TokenService tokenService;

    @Autowired
    public TaskController(TaskService taskService, TokenService tokenService) {
        this.taskService = taskService;
        this.tokenService = tokenService;
    }

    @GetMapping(path = "tasks")
    public List<Task> getTasks(
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        tokenService.validateTokenAtRequest(token);

        return taskService.getTasks();
    }

    @GetMapping(path = "tasks/{operatorId}")
    public List<Task> getTasksByOperatorId(
            @PathVariable("operatorId") Long id,
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        //tokenService.validateTokenAtRequest(token);

        return taskService.getTasksByOperatorId(id);
    }

    @DeleteMapping(path = "tasks/delete/{taskId}")
    public Map<String, Boolean> deleteTaskById(
            @PathVariable("taskId") Long id,
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        tokenService.validateTokenAtRequest(token);

        taskService.deleteTaskById(id);

        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping(path = "tasks/delete")
    public Map<String, Boolean> deleteAllCompletedTasks(
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        tokenService.validateTokenAtRequest(token);

        taskService.deleteAllCompletedTasks();

        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping(path = "tasks/{taskId}/update")
    public Map<String, Boolean> updateTaskStatus(
            @PathVariable("taskId") long id,
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        tokenService.validateTokenAtRequest(token);

        taskService.updateTaskStatus(id);

        response.put("updated", Boolean.TRUE);
        return response;
    }
}
