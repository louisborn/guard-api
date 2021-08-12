package com.guard.restservice.tasks;

import javax.persistence.*;

@Entity
@Table
public class Task {
    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    private long id;
    private String title;
    private String location;
    private String token;
    private boolean hasPriority;

    public Task() {
    }

    public Task(long id, String title, String location, String token, boolean hasPriority) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.token = token;
        this.hasPriority = hasPriority;
    }

    public Task(String title, String location, String token, boolean hasPriority) {
        this.title = title;
        this.location = location;
        this.token = token;
        this.hasPriority = hasPriority;
    }

    public long getId() { return id; }

    public String getTitle() { return title; }

    public String getLocation() { return location; }

    public String getToken() { return token; }

    public boolean getPriority() { return hasPriority; }
}
