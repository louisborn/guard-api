package com.guard.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class OperatorService {

    private final OperatorRepository operatorRepository;

    @Autowired
    public OperatorService(OperatorRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }

    public List<Operator> getOperators() {
        return operatorRepository.findAll();
    }

    public Optional<Operator> getOperatorByToken(String token) {
        return operatorRepository.findOperatorByToken(token);
    }

    public boolean checkTokenValidity(String token) {
        Optional<Operator> operator = operatorRepository.findOperatorByToken(token);
        return operator.isPresent();
    }

    public List<String> startOperatorLogin(String email, String password) {
        Optional<Operator> operator = validateOperatorInput(email, password);
        if(operator.isEmpty()) {
            throw new IllegalStateException("Could not start operator login");
        }
        String token = createOperatorToken(email);
        boolean isLoginSuccessful = updateOperatorToken(token, operator);
        if(!isLoginSuccessful) {
            throw new IllegalStateException("Operator login failed");
        }
        //For debugging only
        List<String> result = new ArrayList<>();
        result.add(operator.get().getEmail());
        result.add(operator.get().getPassword());
        result.add(operator.get().getToken());

        return result;
    }

    public Optional<Operator> validateOperatorInput(String email, String password) {
        Optional<Operator> operator = operatorRepository.findOperatorByEmail(email);
        if (operator.isEmpty()) {
            throw new IllegalStateException("Operator not found");
        }
        if(!Objects.equals(operator.get().getPassword(), password)) {
            throw new IllegalStateException("Wrong password");
        }
        return operator;
    }

    public String createOperatorToken(String email) {
        return Base64.getEncoder().encodeToString(email.getBytes(StandardCharsets.UTF_8));
    }

    @Transactional
    public boolean updateOperatorToken(String token, Optional<Operator> operator) {
        if(operator.isEmpty()) {
            throw new IllegalStateException("Could not update operator");
        }
        Operator operator1 = operator.get();
        operator1.setToken(token);
        operatorRepository.save(operator1);
        return true;
    }
}
