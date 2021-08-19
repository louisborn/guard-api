package com.guard.restservice.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping(path = "handshake")
    public Map<String, String> getHandshake(
            @RequestHeader(name = "X-DEVICE-ID") String deviceId,
            @RequestHeader(name = "X-TOKEN") String token,
            @RequestHeader(name = "X-APP-ID") String applicationId
    ) {
        authenticationService.validateHandshake(deviceId, token, applicationId);
        Map<String, String> response = new HashMap<>();
        switch (authenticationService.getTokenStatus()) {
            case VALID:
                response.put("status", "VALID");
                break;
            case INVALID:
                response.put("status", "INVALID");
                break;
            case EXPIRED:
                response.put("status", "EXPIRED");
                break;
            case REGISTRATION:
                response.put("status", "REGISTRATION");
                break;
        }
        return response;
    }
}
