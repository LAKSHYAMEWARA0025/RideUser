package org.example.rideshare.dto;

import java.util.Date;

public class RideResponse {

    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private String status;
    private Date createdAt;

    public RideResponse(String id, String userId, String driverId,
                        String pickupLocation, String dropLocation,
                        String status, Date createdAt) {

        this.id = id;
        this.userId = userId;
        this.driverId = driverId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.status = status;
        this.createdAt = createdAt;
    }

    // getters (optional setters)
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getDriverId() { return driverId; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropLocation() { return dropLocation; }
    public String getStatus() { return status; }
    public Date getCreatedAt() { return createdAt; }
}
