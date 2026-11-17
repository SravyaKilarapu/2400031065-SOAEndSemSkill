package com.example.ride.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rides")
public class RideController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/test")
    public String test() {
        return "Ride Service handled this request on port " + port;
    }

    @PostMapping
    public String bookRide(@RequestBody(required = false) String rideDetails) {
        return "Ride request received by instance on port " + port
                + ". Details: " + (rideDetails == null ? "No details provided" : rideDetails);
    }
}
