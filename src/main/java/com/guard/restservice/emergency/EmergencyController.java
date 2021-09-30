package com.guard.restservice.emergency;

import com.guard.restservice.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmergencyController {
    /** The response map after a request */
    private final Map<String, String> response = new HashMap<>();

    private final EmergencyService emergencyService;

    /** Used to validate the unique operator token
     *  included in the api requests' header.
     *  If the token validation fails, the request is denied.
     */
    private final TokenService tokenService;

    @Autowired
    EmergencyController(EmergencyService emergencyService, TokenService tokenService) {
        this.emergencyService = emergencyService;
        this.tokenService = tokenService;
    }

    @GetMapping(path = "emergency/get/{operatorId}")
    public List<Emergency> getActiveEmergency(
            @PathVariable("operatorId") Long id,
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        if(tokenService.validateTokenAtRequest(token)) {
            return emergencyService.getEmergencyByOperatorId(id);
        } else {
            return Collections.emptyList();
        }
    }

    @PostMapping(path = "emergency/post")
    public Map<String, String> addEmergency(
            @RequestHeader(name = "X-TOKEN") String token,
            @RequestBody Emergency emergency
    ) {
        if(tokenService.validateTokenAtRequest(token)) {
            emergencyService.addEmergency(token, emergency);
            response.put("STATUS", "SUCCESS");
        } else {
            response.put("STATUS", "UNAUTHORIZED");
        }
        return response;
    }

    @PutMapping(path = "emergency/end/{emergencyId}")
    public Map<String, String> endEmergency(
            @PathVariable(name = "emergencyId") long id,
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        if(tokenService.validateTokenAtRequest(token)) {
            emergencyService.endEmergency(id);
            response.put("STATUS", "SUCCESS");
        } else {
            response.put("STATUS", "UNAUTHORIZED");
        }
        return response;
    }
}
