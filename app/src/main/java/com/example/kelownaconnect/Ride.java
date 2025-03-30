package com.example.kelownaconnect;

import android.os.Parcel;
import android.os.Parcelable;

public class Ride implements Parcelable {
    private String destination;
    private String status;
    private String time;
    private int passengers;
    private String preferences;

    public Ride(String destination, String status, int passengers, String time, String preferences) {
        this.destination = destination;
        this.status = status;
        this.passengers = passengers;
        this.time = time;
        this.preferences = preferences;
    }

    public Ride(String destination, String status, String time){
        this.destination = destination;
        this.status = status;
        this.time = time;
    }

    protected Ride(Parcel in) {
        destination = in.readString();
        status = in.readString();
        time = in.readString();
        passengers = in.readInt();
        preferences = in.readString();
    }

    public static final Creator<Ride> CREATOR = new Creator<Ride>() {
        @Override
        public Ride createFromParcel(Parcel in) {
            return new Ride(in);
        }

        @Override
        public Ride[] newArray(int size) {
            return new Ride[size];
        }
    };

    public String getDestination() {
        return destination;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public int getPassengers() {
        return passengers;
    }

    public String getPreferences() {
        return preferences;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(destination);
        parcel.writeString(status);
        parcel.writeString(time);
        parcel.writeInt(passengers);
        parcel.writeString(preferences);
    }
}
