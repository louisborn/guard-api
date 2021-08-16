package com.guard.restservice;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GuardErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    ModelAndView
    handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return new ModelAndView("error/404");

            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                System.out.println(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ModelAndView("error/500");
            }
        }
        return new ModelAndView("error/error");
    }
}
