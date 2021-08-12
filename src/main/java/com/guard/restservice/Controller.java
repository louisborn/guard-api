package com.guard.restservice;

import com.guard.restservice.tasks.Task;
import com.guard.restservice.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public String showIndex() {
        return "hello";
    }

    @GetMapping("/login_form")
    public String showLoginForm(Model model) {
        Operator operator = new Operator();
        model.addAttribute("operator", operator);
        return "login_form";
    }

    @PostMapping("/authentication")
    public String authenticateOperator(@ModelAttribute Operator operator, Model model) {
        model.addAttribute("operator", operator);
        return "display_form";
    }

    @GetMapping
    public List<Operator> getOperators() {
        return operatorService.getOperators();
    }

    @GetMapping(path = "/operators/{password}")
    public List<Operator> getOperatorsByEmail(@PathVariable("password") String password) {
        return operatorService.getOperatorsByEmail(password);
    }

    @GetMapping(path = "/tasks/{token}")
    public List<Task> getTasks(@PathVariable("token") String token) {
        return taskService.getTasks(token);
    }
}
