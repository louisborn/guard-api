package com.guard.restservice.token;

import com.guard.restservice.operator.Operator;
import com.guard.restservice.operator.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

@Service
public class TokenService {

    private String token;
    private String deviceId;
    private String applicationId;
    private String operatorEmail;

    private TokenStatus tokenStatus;

    private final OperatorService operatorService;

    @Autowired
    public TokenService(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    public String getToken() {
        return token;
    }

    public TokenStatus getTokenStatus() {
        return tokenStatus;
    }

    public void validateHandshake(String deviceId, String token, String applicationId) {
        try {
            if(deviceId.isEmpty()) {
                tokenStatus = TokenStatus.INVALID;
                return;
            }
            Optional<Operator> operator = operatorService.getOperatorByDeviceId(deviceId);
            if(!operator.isPresent()) {
                tokenStatus = TokenStatus.LOGIN_REQUIRED;
                return;
            }
            if(applicationId.isEmpty() || !applicationId.equals(operator.get().getApplicationId())) {
                tokenStatus = TokenStatus.INVALID;
                return;
            }
            this.deviceId = deviceId;
            this.applicationId = applicationId;
            this.operatorEmail = operator.get().getEmail();
            if(token.isEmpty()) {
                generateToken();
                tokenStatus = TokenStatus.NEW;
                return;
            }
            validateToken(token);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void validateToken(String token) {
        try {
            byte[] bytes = Base64.getDecoder().decode(token);
            String decrypted = new String(bytes);

            String[] parts = decrypted.split("~", -2);

            if(deviceId == null || applicationId == null || operatorEmail == null) {
                Optional<Operator> operator = operatorService.getOperatorByEmail(parts[2]);
                if(!operator.isPresent()) {
                    tokenStatus = TokenStatus.LOGIN_REQUIRED;
                    return;
                }
                deviceId = operator.get().getDeviceId();
                applicationId = operator.get().getApplicationId();
                operatorEmail = operator.get().getEmail();
            }

            if(!deviceId.equals(parts[0])) {
                tokenStatus = TokenStatus.INVALID;
                return;
            }
            if(!applicationId.equals(parts[1])) {
                tokenStatus = TokenStatus.INVALID;
                return;
            }
            if(!operatorEmail.equals(parts[2])) {
                tokenStatus = TokenStatus.INVALID;
                return;
            }
            tokenStatus = TokenStatus.VALID;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void generateToken() {
        try {
            LocalDate expirationDate = LocalDate.now();

            //{deviceId}~{applicationId}~{operatorEmail}~{expirationDate}
            String tokenInClear = deviceId + "~" + applicationId + "~" + operatorEmail + "~" + expirationDate;

            token = Base64.getEncoder().encodeToString(tokenInClear.getBytes(StandardCharsets.UTF_8));
            System.out.println(token);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void validateTokenAtRequest(String token) {
        try {
            validateToken(token);
            if(getTokenStatus() == TokenStatus.INVALID) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
