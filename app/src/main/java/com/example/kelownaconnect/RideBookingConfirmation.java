package com.example.kelownaconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RideBookingConfirmation extends AppCompatActivity {

    private TextView pickupLocation, confirmationMessage, driverDetails, destination, eta, vehicleDetails, driverRating;
    private Button homeButton, cancelRideButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_booking_confirmation);

        // Initialize views
        confirmationMessage = findViewById(R.id.confirmationMessage);
        driverDetails = findViewById(R.id.driverDetails);
        pickupLocation = findViewById(R.id.pickupLocation);
        destination = findViewById(R.id.destination);
        eta = findViewById(R.id.eta);
        homeButton = findViewById(R.id.homeButton);
        cancelRideButton = findViewById(R.id.cancelRideButton);
        pickupLocation = findViewById(R.id.pickupLocation);
        vehicleDetails = findViewById(R.id.vehicleDetails);
        driverRating = findViewById(R.id.driverRating);

        // Get the driver and ride details passed from the previous activity
        Intent intent = getIntent();
        Driver driver = (Driver) intent.getSerializableExtra("selectedDriver");
        Ride ride = intent.getParcelableExtra("ride");

        // Set the confirmation message
        confirmationMessage.setText("Your ride has been confirmed!");

        // Set the driver details
        if (driver != null) {
            driverDetails.setText("Driver: " + driver.getName());
            vehicleDetails.setText("Vehicle: " + driver.getVehicle());
            driverRating.setText("Rating: " + driver.getRating() + "/5.0");
        }

//        pickupLocation.setText("Pickup Location: " + ride.getPickupLocation());
//        destination.setText("Destination: " + ride.getDestination());
        assert driver != null;
        eta.setText("Arriving in: " + driver.getEta());

        // Set onClickListener for Home button
        homeButton.setOnClickListener(v -> {
            // Redirect to the Home activity (or main screen)
            Intent homeIntent = new Intent(RideBookingConfirmation.this, MainActivity.class);
            assert ride != null;
            ride.setStatus("Upcoming");
            homeIntent.putExtra("newRide", ride);
            startActivity(homeIntent);
            finish();  // Finish the current activity
        });

        // Set onClickListener for Cancel Ride button (frontend only)
        cancelRideButton.setOnClickListener(v -> {
            // Handle cancel action here (not implemented yet)
        });
    }
}
