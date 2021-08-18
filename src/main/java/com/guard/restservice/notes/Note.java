package com.guard.restservice.notes;

import javax.persistence.*;

@Entity
@Table
public class Note {
    @Id
    @SequenceGenerator(name = "note_sequence", sequenceName = "note_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_sequence")
    private long id;
    private String title;
    private String location;
    private String description;
    private String creator;
    private String time;
    private String date;
    private boolean hasPriority;

    public Note() {
    }

    public Note(
            long id,
            String title,
            String location,
            String description,
            String creator,
            String time,
            String date,
            boolean hasPriority) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.description = description;
        this.creator = creator;
        this.time = time;
        this.date = date;
        this.hasPriority = hasPriority;
    }

    public Note(
            String title,
            String location,
            String description,
            String creator,
            String time,
            String date,
            boolean hasPriority) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.creator = creator;
        this.time = time;
        this.date = date;
        this.hasPriority = hasPriority;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getCreator() {
        return creator;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public boolean getHasPriority() {
        return hasPriority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHasPriority(boolean hasPriority) {
        this.hasPriority = hasPriority;
    }
}
