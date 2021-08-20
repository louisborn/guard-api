package com.guard.restservice.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping(path = "handshake")
    public Map<String, String> getHandshake(
            @RequestHeader(name = "X-DEVICE-ID") String deviceId,
            @RequestHeader(name = "X-TOKEN") String token,
            @RequestHeader(name = "X-APP-ID") String applicationId
    ) {
        tokenService.validateHandshake(deviceId, token, applicationId);
        Map<String, String> response = new HashMap<>();
        switch (tokenService.getTokenStatus()) {
            case VALID:
                response.put("status", "VALID");
                break;
            case INVALID:
                response.put("status", "INVALID");
                break;
            case EXPIRED:
                response.put("status", "EXPIRED");
                break;
            case LOGIN_REQUIRED:
                response.put("status", "LOGIN_REQUIRED");
                break;
            case NEW:
                response.put("status", "NEW");
                response.put("token", tokenService.getToken());
                break;
        }
        return response;
    }
}
