package com.guard.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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

    public List<Operator> getOperatorsByEmail(String password) {
        boolean exists = operatorRepository.findOperatorByEmail(password).isPresent();
        if (!exists) {
            throw new IllegalStateException("denied");
        }
        return operatorRepository.findAll();
    }
}
