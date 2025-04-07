package com.example.kelownaconnect;

import java.io.Serializable;

public class Rider implements Serializable {
    private String name;
    private int passengers;
    private String pickup;
    private String drop;

    public Rider(String drop, String pickup, int passengers, String name) {
        this.drop = drop;
        this.pickup = pickup;
        this.passengers = passengers;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPassengers() {
        return passengers;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDrop() {
        return drop;
    }

}
