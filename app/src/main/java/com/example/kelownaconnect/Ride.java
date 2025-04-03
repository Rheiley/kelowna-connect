package com.example.kelownaconnect;

public class Ride {
    private String destination;
    private String status;
    private String time;

    public Ride(String destination, String status, String time) {
        this.destination = destination;
        this.status = status;
        this.time = time;
    }

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
