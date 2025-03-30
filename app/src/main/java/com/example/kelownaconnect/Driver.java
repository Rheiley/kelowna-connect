package com.example.kelownaconnect;

public class Driver {
    private String name;
    private double rating;
    private double estimatedCost;
    private String eta;
    private String vehicle;

    public Driver(String name, double rating, double estimatedCost, String eta, String vehicle) {
        this.name = name;
        this.rating = rating;
        this.estimatedCost = estimatedCost;
        this.eta = eta;
        this.vehicle = vehicle;
    }

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
}
