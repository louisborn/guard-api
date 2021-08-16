package com.guard.restservice;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    ModelAndView
    handleError() {
        
    }
}
