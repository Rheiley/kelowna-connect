package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DriverDetails extends AppCompatActivity {

    private TextView driverName, driverBio, driverStatus, driverSeats, driverRating, driverVehicle, driverCompletedRides, driverLanguages;
    private Button viewReviewsButton, confirmBookingButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        // Initialize views
        driverName = findViewById(R.id.driverName);
        driverBio = findViewById(R.id.driverBio);
        driverStatus = findViewById(R.id.driverStatus);
        driverSeats = findViewById(R.id.driverSeats);
        driverRating = findViewById(R.id.driverRating);
        driverVehicle = findViewById(R.id.driverVehicle);
        driverCompletedRides = findViewById(R.id.driverCompletedRides);
        driverLanguages = findViewById(R.id.driverLanguages);
        viewReviewsButton = findViewById(R.id.viewReviewsButton);
        confirmBookingButton = findViewById(R.id.confirmBookingButton);
        backButton = findViewById(R.id.backButton);

        // Get the driver data passed through the Intent
        Intent intent = getIntent();
        Driver selectedDriver = (Driver) intent.getSerializableExtra("selectedDriver");

        // Set driver details on the views
        driverName.setText(selectedDriver.getName());
        driverBio.setText(selectedDriver.getBio());
        driverStatus.setText(selectedDriver.getStatus());
        driverSeats.setText(String.valueOf(selectedDriver.getSeatsAvailable()));
        driverRating.setText(String.valueOf(selectedDriver.getRating()));
        driverVehicle.setText(selectedDriver.getVehicle());
        driverCompletedRides.setText(String.valueOf(selectedDriver.getCompletedRides()));
        driverLanguages.setText(selectedDriver.getLanguages());

        // Set onClickListeners for the buttons
        viewReviewsButton.setOnClickListener(v -> openReviewsPage(selectedDriver));
        confirmBookingButton.setOnClickListener(v -> confirmBooking(selectedDriver));
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void openReviewsPage(Driver driver) {
        // Handle review page functionality here, maybe navigate to a new activity or show a dialog
    }

    private void confirmBooking(Driver driver) {
        // Handle booking confirmation logic here
    }
}
