package com.example.kelownaconnect;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Offer implements Parcelable {
    private String destination;
    private int seats;
    private String time;
    private int cost;
    private String name;
    private String pass;
    private String pick;
    private String dest;

    public Offer(String destination, int seats, String time, int cost) {
        this.destination = destination;
        this.seats = seats;
        this.time = time;
        this.cost = cost;
    }

    public Offer(String name, String pass, String pick, String dest) {
        this.name = name;
        this.pass = pass;
        this.pick = pick;
        this.dest = dest;
    }

    protected Offer(Parcel in) {
        destination = in.readString();
        seats = in.readInt();
        time = in.readString();
        cost = in.readInt();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCost() {
        return cost;
    }

    public String getTime() {
        return time;
    }

    public int getSeats() {
        return seats;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getPick() {
        return pick;
    }

    public String getDest() {
        return dest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(destination);
        dest.writeInt(seats);
        dest.writeString(time);
        dest.writeInt(cost);
    }
}
