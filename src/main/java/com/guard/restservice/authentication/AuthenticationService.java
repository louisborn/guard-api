package com.guard.restservice.authentication;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Service
public class TokenAuthentication {

    @GetMapping(path = "handshake")
    public Map<String, Boolean> validateHandshake() {

    }
}
