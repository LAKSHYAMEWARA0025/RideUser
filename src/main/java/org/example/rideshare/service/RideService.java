package org.example.rideshare.service;

import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.NotFoundException;
import org.example.rideshare.model.Ride;
import org.example.rideshare.model.User;
import org.example.rideshare.repository.RideRepository;
import org.example.rideshare.repository.UserRepository;
import org.example.rideshare.util.RideStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    public RideResponse createRide(CreateRideRequest request, Authentication auth) {

        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getRole().equals("ROLE_USER")) {
            throw new BadRequestException("Only USERS can request rides");
        }

        Ride ride = new Ride();
        ride.setUserId(user.getId());
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setStatus(RideStatus.REQUESTED);

        ride = rideRepository.save(ride);

        return toResponse(ride);
    }

    public List<RideResponse> getUserRides(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));

        return rideRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RideResponse> getPendingRides(Authentication auth) {
        User driver = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new NotFoundException("Driver not found"));

        if (!driver.getRole().equals("ROLE_DRIVER")) {
            throw new BadRequestException("Only DRIVERS can view pending rides");
        }

        return rideRepository.findByStatus(RideStatus.REQUESTED)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RideResponse acceptRide(String rideId, Authentication auth) {

        User driver = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new NotFoundException("Driver not found"));

        if (!driver.getRole().equals("ROLE_DRIVER")) {
            throw new BadRequestException("Only DRIVERS can accept rides");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals(RideStatus.REQUESTED)) {
            throw new BadRequestException("Ride is not available to accept");
        }

        ride.setDriverId(driver.getId());
        ride.setStatus(RideStatus.ACCEPTED);

        ride = rideRepository.save(ride);

        return toResponse(ride);
    }

    public RideResponse completeRide(String rideId, Authentication auth) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals(RideStatus.ACCEPTED)) {
            throw new BadRequestException("Ride must be ACCEPTED before completing");
        }

        ride.setStatus(RideStatus.COMPLETED);

        ride = rideRepository.save(ride);

        return toResponse(ride);
    }

    private RideResponse toResponse(Ride ride) {
        return new RideResponse(
                ride.getId(),
                ride.getUserId(),
                ride.getDriverId(),
                ride.getPickupLocation(),
                ride.getDropLocation(),
                ride.getStatus(),
                ride.getCreatedAt()
        );
    }
}
