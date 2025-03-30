package com.example.kelownaconnect;

import java.io.Serializable;

public class Driver implements Serializable {
    private String name;
    private double rating;
    private double estimatedCost;
    private String eta;
    private String vehicle;
    private String bio;
    private String status;
    private int seatsAvailable;
    private int completedRides;
    private String languages;

    // Constructor with new fields
    public Driver(String name, double rating, double estimatedCost, String eta, String vehicle,
                  String bio, String status, int seatsAvailable, int completedRides, String languages) {
        this.name = name;
        this.rating = rating;
        this.estimatedCost = estimatedCost;
        this.eta = eta;
        this.vehicle = vehicle;
        this.bio = bio;
        this.status = status;
        this.seatsAvailable = seatsAvailable;
        this.completedRides = completedRides;
        this.languages = languages;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public String getEta() {
        return eta;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getBio() {
        return bio;
    }

    public String getStatus() {
        return status;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public int getCompletedRides() {
        return completedRides;
    }

    public String getLanguages() {
        return languages;
    }
}
