package com.guard.restservice;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StatusController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    ResponseEntity<?>
    handleError(HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                body.put("status", "NOT_FOUND");
                return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                body.put("status", "FORBIDDEN");
                return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                body.put("status", "INTERNAL_SERVER_ERROR");
                return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        body.put("status", "SOME ERROR OCCURRED");
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}
