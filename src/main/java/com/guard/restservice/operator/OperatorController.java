package com.guard.restservice.operator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class OperatorController {

    private final OperatorService operatorService;

    @Autowired
    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    /** For debugging only */
    @GetMapping(path = "operators")
    public List<Operator> getOperators() {
        return operatorService.getOperators();
    }

    @PutMapping(path = "operators/token")
    public void setNewToken() {
        Optional<Operator> optional = operatorService.getOperatorByEmail("john.williams@bosch.de");
        operatorService.saveOperatorToken(optional, "false-token");
    }

    @GetMapping(path = "/")
    ModelAndView
    showIndex(
            @RequestHeader(name = "X-DEVICE-ID") String deviceId,
            @RequestHeader(name = "X-APP-ID") String applicationId
    ) {
        operatorService.setDeviceId(deviceId);
        operatorService.setApplicationId(applicationId);

        return new ModelAndView("hello");
    }

    @GetMapping(path = "registration")
    ModelAndView showRegistrationForm(
            Model model
    ) {
        Operator operator = new Operator();
        model.addAttribute("operator", operator);

        return new ModelAndView("registration");
    }

    @GetMapping(path = "/registration_success")
    ModelAndView redirect() {
        return new ModelAndView("authenticated");
    }

    @PostMapping(path = "/authenticated")
    ModelAndView handleInput(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        if(operatorService.registration(email, password)) {
            return new ModelAndView("redirect:/registration_success");
        }
        return new ModelAndView("registration");
    }

}
