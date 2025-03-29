package com.example.kelownaconnect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.android.libraries.places.api.Places;

import java.util.Calendar;
import java.util.TimeZone;


public class BookRide extends AppCompatActivity {

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

        // Views
        EditText pickupLocation = findViewById(R.id.pickupLocation);
        EditText dropoffLocation = findViewById(R.id.dropoffLocation);
        EditText passengerCount = findViewById(R.id.passengerCount);
        EditText departureTime = findViewById(R.id.departureTime);
        EditText carpoolPreferences = findViewById(R.id.carpoolPreferences);
        Button bookRideButton = findViewById(R.id.bookRideButton);

        // Disable text input and enable click behavior
        departureTime.setFocusable(false);
        departureTime.setClickable(true);
        departureTime.setOnClickListener(v -> {
            // Get the current time
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // Create the MaterialTimePicker
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setHour(hour)
                    .setMinute(minute)
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD) // Allows users to type the time (optional)
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
                departureTime.setText(formattedTime);
            });

            // Show the time picker
            materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");
        });
    }
}