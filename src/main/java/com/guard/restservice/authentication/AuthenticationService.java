package com.guard.restservice.authentication;

import com.guard.restservice.operator.Operator;
import com.guard.restservice.operator.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;

enum TokenStatus {
    INVALID,
    EXPIRED,
    VALID,
    REGISTRATION,
}

@Service
public class AuthenticationService {

    private final OperatorService operatorService;

    private String token;

    private TokenStatus tokenStatus;

    public TokenStatus getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(TokenStatus tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    private String deviceId;
    private String applicationId;
    private String operatorEmail;
    private LocalDate expirationDate;

    @Autowired
    public AuthenticationService(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    public void validateHandshake(String deviceId, String token, String applicationId) {
        if(deviceId.isEmpty()) {
            tokenStatus = TokenStatus.INVALID;
        }
        Optional<Operator> operator = operatorService.getOperatorByDeviceId(deviceId);
        if(!operator.isPresent()) {
            tokenStatus = TokenStatus.REGISTRATION;
        }
        if(applicationId.isEmpty() || !applicationId.equals(operator.get().getApplicationId())) {
            tokenStatus = TokenStatus.INVALID;
        }
        this.deviceId = deviceId;
        this.applicationId = applicationId;
        this.operatorEmail = operator.get().getEmail();
        if(token.isEmpty()) {
            generateToken();
        }
        validateToken(token);
    }

    public void validateToken(String token) {
        try {
            byte[] bytes = Base64.getDecoder().decode(token);
            String decrypted = new String(bytes);

            String[] parts = decrypted.split("~", -2);

            if(!deviceId.equals(parts[0])) {
                tokenStatus = TokenStatus.INVALID;
            }
            if(!applicationId.equals(parts[1])) {
                tokenStatus = TokenStatus.INVALID;
            }
            if(!operatorEmail.equals(parts[2])) {
                tokenStatus = TokenStatus.INVALID;
            }

            int span = LocalDate.now().getDayOfMonth() - expirationDate.getDayOfMonth();
            if(span > 0 ) {
                tokenStatus = TokenStatus.EXPIRED;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void generateToken() {
        this.expirationDate = LocalDate.now().plus(1, ChronoUnit.DAYS);

        //{deviceId}~{applicationId}~{operatorEmail}~{expirationDate}
        String tokenInClear = deviceId + "~" + applicationId + "~" + operatorEmail + "~" + expirationDate;

        this.token = Base64.getEncoder().encodeToString(tokenInClear.getBytes(StandardCharsets.UTF_8));
    }
}
