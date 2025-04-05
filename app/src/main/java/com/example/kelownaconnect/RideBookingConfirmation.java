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

    private TextView labelDriver, valueDriver, labelVehicle, valueVehicle, labelRating, valueRating,
            labelPickupLocation, valuePickupLocation, labelDestination, valueDestination, labelEta, valueEta;
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

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed(){
        // Do nothing because the user should not be able to go back to the previous page from here
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
    private void setConfirmationDetails() {
        assert driver != null;
        labelDriver.setText("Driver: ");
        valueDriver.setText(driver.getName());

        labelVehicle.setText("Vehicle: ");
        valueVehicle.setText(driver.getVehicle());

        labelRating.setText("Rating: ");
        valueRating.setText(driver.getRating() + "/5.0");

        labelPickupLocation.setText("Pickup Location: ");
        valuePickupLocation.setText(ride.getPickupLocation());

        labelDestination.setText("Destination: ");
        valueDestination.setText(ride.getDestination());

        labelEta.setText("Arriving in: ");
        valueEta.setText(driver.getEta());
    }

    private void getDriverAndRideDetailsFromPreviousActivity(){
        Intent intent = getIntent();
        driver = (Driver) intent.getSerializableExtra("selectedDriver");
        ride = intent.getParcelableExtra("ride");
    }

    private void initializeViews(){
        labelDriver = findViewById(R.id.labelDriver);
        valueDriver = findViewById(R.id.valueDriver);

        labelVehicle = findViewById(R.id.labelVehicle);
        valueVehicle = findViewById(R.id.valueVehicle);

        labelRating = findViewById(R.id.labelRating);
        valueRating = findViewById(R.id.valueRating);

        labelPickupLocation = findViewById(R.id.labelPickupLocation);
        valuePickupLocation = findViewById(R.id.valuePickupLocation);

        labelDestination = findViewById(R.id.labelDestination);
        valueDestination = findViewById(R.id.valueDestination);

        labelEta = findViewById(R.id.labelEta);
        valueEta = findViewById(R.id.valueEta);

        homeButton = findViewById(R.id.homeButton);
    }
}
