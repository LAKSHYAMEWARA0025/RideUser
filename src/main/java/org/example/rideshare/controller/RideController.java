package org.example.rideshare.controller;

import jakarta.validation.Valid;
import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/rides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RideResponse> createRide(
            @Valid @RequestBody CreateRideRequest request,
            Authentication auth
    ) {
        return ResponseEntity.ok(rideService.createRide(request, auth));
    }

    @GetMapping("/user/rides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<RideResponse>> getUserRides(Authentication auth) {
        return ResponseEntity.ok(rideService.getUserRides(auth));
    }

    @PostMapping("/rides/{id}/complete")
    @PreAuthorize("hasAnyRole('USER','DRIVER')")
    public ResponseEntity<RideResponse> completeRide(
            @PathVariable String id,
            Authentication auth
    ) {
        return ResponseEntity.ok(rideService.completeRide(id, auth));
    }
}
