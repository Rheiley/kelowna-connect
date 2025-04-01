package com.example.kelownaconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RideBookingConfirmation extends AppCompatActivity {

    private TextView pickupLocation, driverDetails, destination, eta, vehicleDetails, driverRating;
    private Button homeButton;
    private Driver driver;
    private Ride ride;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_booking_confirmation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        getDriverAndRideDetailsFromPreviousActivity();
        setConfirmationDetails();
        setupOnClickListeners();
    }

    private void setupOnClickListeners(){
        homeButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(RideBookingConfirmation.this, MainActivity.class);
            assert ride != null;
            ride.setStatus("Upcoming");
            homeIntent.putExtra("newRide", ride);
            startActivity(homeIntent);
            finish();
        });
    }

    @SuppressLint("SetTextI18n")
    private void setConfirmationDetails(){
        assert driver != null;
        driverDetails.setText("Driver: " + driver.getName());
        vehicleDetails.setText("Vehicle: " + driver.getVehicle());
        driverRating.setText("Rating: " + driver.getRating() + "/5.0");
//        pickupLocation.setText("Pickup Location: " + ride.getPickupLocation());
//        destination.setText("Destination: " + ride.getDestination());
        eta.setText("Arriving in: " + driver.getEta());
    }

    private void getDriverAndRideDetailsFromPreviousActivity(){
        Intent intent = getIntent();
        driver = (Driver) intent.getSerializableExtra("selectedDriver");
        ride = intent.getParcelableExtra("ride");
    }

    private void initializeViews(){
        driverDetails = findViewById(R.id.driverDetails);
        pickupLocation = findViewById(R.id.pickupLocation);
        destination = findViewById(R.id.destination);
        eta = findViewById(R.id.eta);
        homeButton = findViewById(R.id.homeButton);
        pickupLocation = findViewById(R.id.pickupLocation);
        vehicleDetails = findViewById(R.id.vehicleDetails);
        driverRating = findViewById(R.id.driverRating);
    }
}
