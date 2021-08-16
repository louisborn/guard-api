package com.guard.restservice;

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
    private String token;

    public Operator() {
    }

    public Operator(long id, String name, String email, String password, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public Operator(String name, String email, String password, String token) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;
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

    public String getToken() { return token; }
}