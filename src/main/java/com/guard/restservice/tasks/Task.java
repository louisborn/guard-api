package com.guard.restservice.tasks;

import javax.persistence.*;

@Entity
@Table
public class Task {
    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    private long id;
    private long operatorId;
    private String title;
    private String location;
    private String status;
    private String time;
    private String date;
    private String description;
    private boolean hasPriority;

    public Task() {
    }

    public Task(long id,
                long operatorId,
                String title,
                String location,
                String status,
                String time,
                String date,
                String description,
                boolean hasPriority) {
        this.id = id;
        this.operatorId = operatorId;
        this.title = title;
        this.location = location;
        this.status = status;
        this.time = time;
        this.date = date;
        this.description = description;
        this.hasPriority = hasPriority;
    }

    public Task(
            long operatorId,
            String title,
            String location,
            String status,
            String time,
            String date,
            String description,
            boolean hasPriority) {
        this.operatorId = operatorId;
        this.title = title;
        this.location = location;
        this.status = status;
        this.time = time;
        this.date = date;
        this.description = description;
        this.hasPriority = hasPriority;
    }

    public long getId() {
        return id;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHasPriority() {
        return hasPriority;
    }
}
