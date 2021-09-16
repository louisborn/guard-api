package com.guard.restservice.locations;

import javax.persistence.*;

@Entity
@Table
public class Location {
    @Id
    @SequenceGenerator(name = "location_sequence", sequenceName = "location_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_sequence")
    private long id;
    private String building;
    private String floor;
    private String room;

    public Location(long id, String building, String floor, String room) {
        this.id = id;
        this.building = building;
        this.floor = floor;
        this.room = room;
    }

    public Location(String building, String floor, String room) {
        this.building = building;
        this.floor = floor;
        this.room = room;
    }

    public long getId() {
        return id;
    }

    public String getBuilding() {
        return building;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoom() {
        return room;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
