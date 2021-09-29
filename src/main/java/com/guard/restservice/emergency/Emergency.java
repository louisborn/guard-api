package com.guard.restservice.emergency;

import javax.persistence.*;
import java.util.List;

class EmergencyCase {
    private List<String> emergencyCases;

    public List<String> getEmergencyCases() {
        return emergencyCases;
    }

    public void setEmergencyCases(List<String> emergencyCases) {
        this.emergencyCases = emergencyCases;
    }
}

@Entity
@Table
public class Emergency {
    @Id
    @SequenceGenerator(name = "emergency_sequence", sequenceName = "emergency_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emergency_sequence")
    private long id;
    private long operatorId;
    private double affectedPeople;
    private String sender;
    private String time;
    private String date;
    @ElementCollection
    private List<String> emergencyCases;
    private boolean isActive;

    public Emergency() {
    }

    public Emergency(long operatorId, double affectedPeople, String sender, String time, String date, List<String> emergencyCases, boolean isActive) {
        this.operatorId = operatorId;
        this.affectedPeople = affectedPeople;
        this.sender = sender;
        this.time = time;
        this.date = date;
        this.emergencyCases = emergencyCases;
        this.isActive = isActive;
    }

    public Emergency(long id, long operatorId, double affectedPeople, String sender, String time, String date, List<String> emergencyCases, boolean isActive) {
        this.id = id;
        this.operatorId = operatorId;
        this.affectedPeople = affectedPeople;
        this.sender = sender;
        this.time = time;
        this.date = date;
        this.emergencyCases = emergencyCases;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public String getSender() {
        return sender;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public double getAffectedPeople() {
        return affectedPeople;
    }

    public List<String> getEmergencyCases() {
        return emergencyCases;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setAffectedPeople(double affectedPeople) {
        this.affectedPeople = affectedPeople;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEmergencyCases(List<String> emergencyCases) {
        this.emergencyCases = emergencyCases;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
