package com.guard.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public List<String> getOperatorByEmailAndPassword(String email, String password) {
        Optional<Operator> operator = operatorRepository.findOperatorByEmail(email);
        if (operator.isEmpty()) {
            throw new IllegalStateException("Operator not found");
        }
        if(!Objects.equals(operator.get().getPassword(), password)) {
            throw new IllegalStateException("Wrong password");
        }

        List<String> result = new ArrayList<String>();
        result.add(operator.get().getEmail());
        result.add(operator.get().getPassword());

        return result;
    }
}
