package com.guard.restservice.locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LocationController {
    /** The response map after a request */
    private final Map<String, String> response = new HashMap<>();

    private final LocationService locationService;

    @Autowired
    LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(path = "locations")
    public List<Location> getLocations() {
        return locationService.getLocations();
    }
}
