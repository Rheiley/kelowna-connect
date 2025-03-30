package com.example.kelownaconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class BookRide extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private EditText activeLocationField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_ride);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the Places API
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), BuildConfig.GOOGLE_MAPS_API_KEY);
        }
        PlacesClient placesClient = Places.createClient(this);

        // Views
        EditText pickupLocation = findViewById(R.id.pickupLocation);
        EditText dropoffLocation = findViewById(R.id.dropoffLocation); // Store reference
        EditText passengerCount = findViewById(R.id.passengerCount);
        EditText departureTime = findViewById(R.id.departureTime);
        EditText carpoolPreferences = findViewById(R.id.carpoolPreferences);
        Button findARideButton = findViewById(R.id.findARideButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(v -> {
            // Handle cancel button click
            finish();
        });

        findARideButton.setOnClickListener(v -> {
            // Get input values
            String pickup = pickupLocation.getText().toString().trim();
            String dropoff = dropoffLocation.getText().toString().trim();
            String passengers = passengerCount.getText().toString().trim();
            String departure = departureTime.getText().toString().trim();
            String preferences = carpoolPreferences.getText().toString().trim();

            // Check for empty fields and show a toast for missing inputs
            if (pickup.isEmpty()) {
                Toast.makeText(BookRide.this, "Please enter a pickup location", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dropoff.isEmpty()) {
                Toast.makeText(BookRide.this, "Please enter a dropoff location", Toast.LENGTH_SHORT).show();
                return;
            }
            if (passengers.isEmpty()) {
                Toast.makeText(BookRide.this, "Please enter the number of passengers", Toast.LENGTH_SHORT).show();
                return;
            }
            if (departure.isEmpty()) {
                Toast.makeText(BookRide.this, "Please select a departure time", Toast.LENGTH_SHORT).show();
                return;
            }

            // TO-DO: Implement the logic to find a ride based on the input values
            Ride ride = new Ride(pickup, dropoff, Integer.parseInt(passengers), departure, preferences);





        });

        // Open Autocomplete search when clicking dropoffLocation
        dropoffLocation.setFocusable(false);
        dropoffLocation.setClickable(true);
        dropoffLocation.setOnClickListener(v -> {
            activeLocationField = dropoffLocation;
            openPlaceSearch();
        });

        // Open Autocomplete search when clicking pickupLocation
        pickupLocation.setFocusable(false);
        pickupLocation.setClickable(true);
        pickupLocation.setOnClickListener(v -> {
            activeLocationField = pickupLocation;
            openPlaceSearch();
        });

        // Disable text input and enable click behavior for departure time
        departureTime.setFocusable(false);
        departureTime.setClickable(true);
        departureTime.setOnClickListener(v -> openTimePicker());
    }

    private void openPlaceSearch() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        // Open Places Autocomplete Intent
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(BookRide.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (activeLocationField != null) {
                    activeLocationField.setText(place.getName());
                }
                Log.i("BookRide", "Selected Place: " + place.getName() + ", " + place.getLatLng());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("BookRide", "Error: " + status.getStatusMessage());
            }
        }
    }

    private void openTimePicker() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create the MaterialTimePicker
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setHour(hour)
                .setMinute(minute)
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .build();

        // Set listener to update EditText when time is selected
        materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
            int selectedHour = materialTimePicker.getHour();
            int selectedMinute = materialTimePicker.getMinute();

            // Get the current timezone
            TimeZone timeZone = TimeZone.getDefault();
            String timeZoneDisplay = timeZone.getDisplayName(false, TimeZone.SHORT);

            // Format the selected time and append the timezone
            String formattedTime = String.format("%02d:%02d %s", selectedHour, selectedMinute, timeZoneDisplay);

            // Set the departure time with the timezone
            EditText departureTime = findViewById(R.id.departureTime);
            departureTime.setText(formattedTime);
        });

        // Show the time picker
        materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");
    }
}
