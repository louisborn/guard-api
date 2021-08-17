package com.guard.restservice;

import com.guard.restservice.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class GViewController {

    private final OperatorService operatorService;

    @Autowired
    public GViewController(OperatorService operatorService, TaskService taskService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/")
    ModelAndView
    showIndex() {
        return new ModelAndView("hello");
    }

    @GetMapping("/login")
    ModelAndView
    showLoginForm(Model model) {
        Operator operator = new Operator();
        model.addAttribute("operator", operator);
        return new ModelAndView("login");
    }

    @PostMapping("/profile")
    ModelAndView
    handleInput(@RequestParam("email") String email, @RequestParam("password") String password) {
        ModelAndView mav = new ModelAndView("result");
        List<String> operator = operatorService.startOperatorLogin(email, password);
        // For debugging only
        mav.addObject("email", operator.get(0));
        mav.addObject("password", operator.get(1));
        mav.addObject("token", operator.get(2));
        return mav;
    }

}
