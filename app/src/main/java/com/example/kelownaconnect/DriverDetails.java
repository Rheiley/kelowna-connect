package com.example.kelownaconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DriverDetails extends AppCompatActivity {

    private TextView driverName, driverStatus, driverSeats, driverRating, driverVehicle, driverCompletedRides, driverLanguages;
    private Button viewReviewsButton, confirmBookingButton;
    private LinearLayout backButton;
    private Ride ride;
    private Driver selectedDriver;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        initializeViews();
        getDriverAndRideFromIntent();
        setDriverDetails();
        setupOnClickListeners();
    }

    private void setupOnClickListeners(){
        viewReviewsButton.setOnClickListener(v -> openReviewsPage(selectedDriver));
        confirmBookingButton.setOnClickListener(v -> confirmBooking(selectedDriver));
        backButton.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void setDriverDetails(){
        driverName.setText(selectedDriver.getName());
        driverStatus.setText(selectedDriver.getStatus());
        driverSeats.setText(String.valueOf(selectedDriver.getSeatsAvailable()));
        driverRating.setText(selectedDriver.getRating() + "/5.0");
        driverVehicle.setText(selectedDriver.getVehicle());
        driverCompletedRides.setText(String.valueOf(selectedDriver.getCompletedRides()));
        driverLanguages.setText(selectedDriver.getLanguages());
    }

    private void getDriverAndRideFromIntent(){
        Intent intent = getIntent();
        selectedDriver = (Driver) intent.getSerializableExtra("selectedDriver");
        ride = intent.getParcelableExtra("ride");
    }

    private void initializeViews(){
        driverName = findViewById(R.id.driverName);
        driverStatus = findViewById(R.id.driverStatus);
        driverSeats = findViewById(R.id.driverSeats);
        driverRating = findViewById(R.id.driverRating);
        driverVehicle = findViewById(R.id.driverVehicle);
        driverCompletedRides = findViewById(R.id.driverCompletedRides);
        driverLanguages = findViewById(R.id.driverLanguages);
        viewReviewsButton = findViewById(R.id.viewReviewsButton);
        confirmBookingButton = findViewById(R.id.confirmBookingButton);
        backButton = findViewById(R.id.backButtonContainer);
    }

    private void openReviewsPage(Driver driver) {
        // Handle review page functionality here, maybe navigate to a new activity or show a dialog
    }

    private void confirmBooking(Driver driver) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Booking")
                .setMessage("Are you sure you want to book a ride with " + driver.getName() + "?")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // Handle the booking confirmation
                    Intent confirmationIntent = new Intent(DriverDetails.this, RideBookingConfirmation.class);
                    confirmationIntent.putExtra("selectedDriver", driver);
                    confirmationIntent.putExtra("ride", ride);
                    startActivity(confirmationIntent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle the cancel action
                    dialog.dismiss();
                })
                .show();
    }
}
