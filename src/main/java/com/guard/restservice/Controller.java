package com.guard.restservice;

import com.guard.restservice.tasks.Task;
import com.guard.restservice.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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
    ModelAndView
    showIndex() {
        ModelAndView mav = new ModelAndView("hello");
        return mav;
    }

    @GetMapping("/login")
    ModelAndView
    showLoginForm(Model model) {
        Operator operator = new Operator();
        model.addAttribute("operator", operator);
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @PostMapping("/save")
    ModelAndView
    handleInput(@RequestParam("email") String email, @RequestParam("password") String password) {
        ModelAndView mav = new ModelAndView("result");
        List<String> operator = operatorService.getOperatorByEmailAndPassword(email, password);
        mav.addObject("email", operator.get(0));
        mav.addObject("password", operator.get(1));
        return mav;
    }

    @GetMapping
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
