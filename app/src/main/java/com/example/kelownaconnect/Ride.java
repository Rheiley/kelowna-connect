package com.example.kelownaconnect;

public class Ride {
    private String pickupLocation;
    private String dropoffLocation;
    private int passengerCount;
    private String departureTime;
    private String carpoolPreferences;
    private String destination;
    private String status;
    private String time;

    // Constructor for booking a ride
    public Ride(String pickupLocation, String dropoffLocation, int passengerCount, String departureTime, String carpoolPreferences) {
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.passengerCount = passengerCount;
        this.departureTime = departureTime;
        this.carpoolPreferences = carpoolPreferences;
    }

    // Constructor for displaying recent rides (destination, status, time)
    public Ride(String destination, String status, String time) {
        this.destination = destination;
        this.status = status;
        this.time = time;
    }

    // Getters for booking ride fields
    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getCarpoolPreferences() {
        return carpoolPreferences;
    }

    // Getters for recent ride fields
    public String getDestination() {
        return destination;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }
}
