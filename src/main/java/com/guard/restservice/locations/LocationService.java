package com.guard.restservice.locations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getLocations() {
        return locationRepository.findAll();
    }
}
