package com.guard.restservice.operator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class OperatorService {

    private String deviceId;
    private String applicationId;

    private final OperatorRepository operatorRepository;

    @Autowired
    public OperatorService(OperatorRepository operatorRepository) {

        this.operatorRepository = operatorRepository;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public boolean registration(String email, String password) {
        try {
            Optional<Operator> operator = validateCredentials(email, password);
            saveDeviceIdAndApplicationId(operator);
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<Operator> validateCredentials(String email, String password) {
        try {
            Optional<Operator> operator = operatorRepository.findOperatorByEmail(email);
            if (!operator.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            if(!Objects.equals(operator.get().getPassword(), password)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            return operator;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void saveDeviceIdAndApplicationId(Optional<Operator> operator) {
        try {
            if(!operator.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            Operator operator1 = operator.get();

            if(this.deviceId.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            if(this.applicationId.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            operator1.setDeviceId(this.deviceId);
            operator1.setApplicationId(this.applicationId);
            operatorRepository.save(operator1);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Operator> getOperators() {
        return operatorRepository.findAll();
    }

    public Optional<Operator> getOperatorByDeviceId(String deviceId) {
        return operatorRepository.findOperatorByDeviceId(deviceId);
    }

    public Optional<Operator> getOperatorByEmail(String email) {
        return operatorRepository.findOperatorByEmail(email);
    }

    public Optional<Operator> getOperatorByToken(String token) {
        return operatorRepository.findOperatorByToken(token);
    }
}
