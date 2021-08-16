package com.guard.restservice;

import com.guard.restservice.tasks.Task;
import com.guard.restservice.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
//@RequestMapping(path = "api/v1/guard")
public class Controller {

    private final OperatorService operatorService;
    private final TaskService taskService;

    @Autowired
    public Controller(OperatorService operatorService, TaskService taskService) {
        this.operatorService = operatorService;
        this.taskService = taskService;
    }



    @GetMapping("/operators")
    public List<Operator> getOperators() {
        return operatorService.getOperators();
    }

    /*@GetMapping(path = "/operators/{password}")
    public List<Operator> getOperatorsByEmail(@PathVariable("password") String password) {
        return operatorService.getOperatorByEmailAndPassword(password);
    }*/

    @GetMapping(path = "/tasks/{token}")
    public List<Task> getTasks(@PathVariable("token") String token) {
        return taskService.getTasks(token);
    }
}
