package org.example.rideshare.controller;

import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverRideController {

    @Autowired
    private RideService rideService;

    @GetMapping("/rides/requests")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<List<RideResponse>> viewPendingRides(Authentication auth) {
        return ResponseEntity.ok(rideService.getPendingRides(auth));
    }

    @PostMapping("/rides/{id}/accept")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<RideResponse> acceptRide(
            @PathVariable String id,
            Authentication auth
    ) {
        return ResponseEntity.ok(rideService.acceptRide(id, auth));
    }
}
