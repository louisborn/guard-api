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
    private String time;
    private String date;
    private String description;
    private boolean isCompleted;
    private boolean hasPriority;

    public Task() {
    }

    public Task(long id,
                long operatorId,
                String title,
                String location,
                String time,
                String date,
                String description,
                boolean isCompleted,
                boolean hasPriority) {
        this.id = id;
        this.operatorId = operatorId;
        this.title = title;
        this.location = location;
        this.time = time;
        this.date = date;
        this.description = description;
        this.isCompleted = isCompleted;
        this.hasPriority = hasPriority;
    }

    public Task(
            long operatorId,
            String title,
            String location,
            String time,
            String date,
            String description,
            boolean isCompleted,
            boolean hasPriority) {
        this.operatorId = operatorId;
        this.title = title;
        this.location = location;
        this.time = time;
        this.date = date;
        this.description = description;
        this.isCompleted = isCompleted;
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

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsCompleted() { return isCompleted; }

    public boolean getHasPriority() {
        return hasPriority;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setHasPriority(boolean hasPriority) {
        this.hasPriority = hasPriority;
    }
}
