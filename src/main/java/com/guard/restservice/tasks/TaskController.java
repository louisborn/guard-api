package com.guard.restservice.tasks;

import com.guard.restservice.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    /** The response map after a request */
    private final Map<String, String> response = new HashMap<>();

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
        if(tokenService.validateTokenAtRequest(token)) {
            return taskService.getTasksByOperatorId(id);
        }
        return Collections.emptyList();
    }

    @DeleteMapping(path = "tasks/{taskId}/delete")
    public Map<String, String> deleteTaskById(
            @PathVariable("taskId") Long id,
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        if(tokenService.validateTokenAtRequest(token)) {
            taskService.deleteTaskById(id);

            response.put("STATUS", "DELETED");
        } else {
            response.put("STATUS", "UNAUTHORIZED");
        }
        return response;
    }

    @DeleteMapping(path = "tasks/delete")
    public Map<String, String> deleteAllCompletedTasks(
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        if(tokenService.validateTokenAtRequest(token)) {
            taskService.deleteAllCompletedTasks(token);

            response.put("STATUS", "DELETED_COMPLETED");
        } else {
            response.put("STATUS", "UNAUTHORIZED");
        }
        return response;
    }

    @PutMapping(path = "tasks/{taskId}/update")
    public Map<String, String> updateTaskStatus(
            @PathVariable("taskId") long id,
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        if(tokenService.validateTokenAtRequest(token)) {
            taskService.updateTaskStatus(id);

            response.put("STATUS", "UPDATED");
        }
        else {
            response.put("STATUS", "UNAUTHORIZED");
        }
        return response;
    }
}
