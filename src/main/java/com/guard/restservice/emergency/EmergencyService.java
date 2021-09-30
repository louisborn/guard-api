package com.guard.restservice.emergency;

import com.guard.restservice.LocalCalculation;
import com.guard.restservice.operator.Operator;
import com.guard.restservice.operator.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmergencyService {

    /** Used to calculate the current local time and date. */
    private final LocalCalculation globalService;

    private final EmergencyRepository emergencyRepository;

    private final OperatorService operatorService;

    @Autowired
    EmergencyService(LocalCalculation globalService, EmergencyRepository emergencyRepository, OperatorService operatorService) {
        this.globalService = globalService;
        this.emergencyRepository = emergencyRepository;
        this.operatorService = operatorService;
    }

    public List<Emergency> getEmergencyByOperatorId(long id) {
        try {
            return emergencyRepository.findEmergencyByOperatorId(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void addEmergency(String token, Emergency emergency) {
        try {
            Optional<Operator> operator = operatorService.getOperatorByToken(token);

            operator.ifPresent(value -> emergency.setSender(value.getName()));
            emergency.setTime(globalService.calculateLocalTime());
            emergency.setDate(globalService.calculateLocalDate());

            emergencyRepository.save(emergency);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void endEmergency(long id) {
        try {
            Emergency emergency = emergencyRepository.findEmergencyById(id);
            emergency.setActive(false);
            emergency.setEndDate(globalService.calculateLocalTime());

            emergencyRepository.save(emergency);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
