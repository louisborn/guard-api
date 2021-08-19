package com.guard.restservice.operator;

import javax.persistence.*;

@Entity
@Table
public class Operator {
    @Id
    @SequenceGenerator(name = "operator_sequence", sequenceName = "operator_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operator_sequence")
    private long id;
    private String name;
    private String email;
    private String password;
    private String deviceId;
    private String token;
    private String applicationId;

    public Operator() {
    }

    public Operator(long id, String name, String email, String password, String deviceId, String token, String applicationId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.deviceId = deviceId;
        this.token = token;
        this.applicationId = applicationId;
    }

    public Operator(String name, String email, String password, String deviceId, String token, String applicationId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.deviceId = deviceId;
        this.token = token;
        this.applicationId = applicationId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    public String getDeviceId() {
        return deviceId;
    }

    public String getToken() { return token; }

    public String getApplicationId() {
        return applicationId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}