package com.guard.restservice.token;

import com.guard.restservice.operator.Operator;
import com.guard.restservice.operator.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Service
public class TokenService {

    private Optional<Operator> operator;

    private String token;
    private String deviceId;
    private String applicationId;
    private long operatorId;
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

    public long getOperatorId() { return operatorId; }

    public TokenStatus getTokenStatus() {
        return tokenStatus;
    }

    public void validateHandshake(String deviceId, String token, String applicationId) {
        try {
            if(deviceId.isEmpty()) {
                tokenStatus = TokenStatus.INVALID;
                return;
            }
            operator = operatorService.getOperatorByDeviceId(deviceId);
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
            this.operatorId = operator.get().getId();
            this.operatorEmail = operator.get().getEmail();
            if(Objects.equals(token, "")) {
                generateToken();
                operatorService.saveOperatorToken(operator, this.token);
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

            if(deviceId.isEmpty() || applicationId.isEmpty() || operatorEmail.isEmpty()) {
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
            String expirationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(!expirationDate.equals(parts[3])) {
                tokenStatus = TokenStatus.INVALID;
                return;
            }
            operatorService.saveOperatorToken(operator, token);
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
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean validateTokenAtRequest(String token) {
        try {
            byte[] bytes = Base64.getDecoder().decode(token);
            String decrypted = new String(bytes);

            String[] parts = decrypted.split("~", -2);

            Optional<Operator> operator = operatorService.getOperatorByDeviceId(parts[0]);
            if(!operator.isPresent()) {
                return false;
            }

            if(!operator.get().getApplicationId().equals(parts[1])) {
                return false;
            }
            if(!operator.get().getEmail().equals(parts[2])) {
                return false;
            }
            String expirationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(!expirationDate.equals(parts[3])) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
