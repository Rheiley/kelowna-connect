package com.example.kelownaconnect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class BookRide extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private EditText pickupLocation, destination, numberOfPassengers, departureTime, carpoolPreferences, activeLocationField;
    Button findARideButton;
    LinearLayout backButton;
    private final List<String> selectedPreferencesList = new ArrayList<>();

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

        initializePlacesAPI();
        initializeViews();
        setupOnClickListeners();
        setFocusableElements();
        setClickableElements();
    }

    private void setClickableElements(){
        pickupLocation.setClickable(true);
        destination.setClickable(true);
        numberOfPassengers.setClickable(true);
        departureTime.setClickable(true);
        carpoolPreferences.setClickable(true);
    }

    private void setFocusableElements(){
        pickupLocation.setFocusable(false);
        destination.setFocusable(false);
        numberOfPassengers.setFocusable(false);
        departureTime.setFocusable(false);
        carpoolPreferences.setFocusable(false);
    }

    private void setupOnClickListeners(){
        backButton.setOnClickListener(v -> finish());
        findARideButton.setOnClickListener(v -> {
            // Get input values
            String pickupLocation = this.pickupLocation.getText().toString().trim();
            String destination = this.destination.getText().toString().trim();
            String numberOfPassengers = this.numberOfPassengers.getText().toString().trim();
            String departureTime = this.departureTime.getText().toString().trim();
            String carpoolPreferences = this.carpoolPreferences.getText().toString().trim();

            // Check for empty fields and show a toast for missing inputs
            if (pickupLocation.isEmpty()) {
                Toast.makeText(BookRide.this, "Please enter a pickup location", Toast.LENGTH_SHORT).show();
                return;
            }
            if (destination.isEmpty()) {
                Toast.makeText(BookRide.this, "Please enter a destination", Toast.LENGTH_SHORT).show();
                return;
            }
            if (numberOfPassengers.isEmpty()) {
                Toast.makeText(BookRide.this, "Please enter the number of passengers", Toast.LENGTH_SHORT).show();
                return;
            }
            if (departureTime.isEmpty()) {
                Toast.makeText(BookRide.this, "Please select a departure time", Toast.LENGTH_SHORT).show();
                return;
            }

            Ride ride = new Ride(pickupLocation, destination, Integer.parseInt(numberOfPassengers), departureTime, carpoolPreferences);
            Intent intent = new Intent(this, FindARide.class);
            intent.putExtra("ride", ride);
            startActivity(intent);
        });
        destination.setOnClickListener(v -> {
            activeLocationField = destination;
            openPlaceSearch();
        });
        pickupLocation.setOnClickListener(v -> {
            activeLocationField = pickupLocation;
            openPlaceSearch();
        });
        numberOfPassengers.setOnClickListener(v -> openPassengerPicker());
        departureTime.setOnClickListener(v -> openTimePicker());
        carpoolPreferences.setOnClickListener(v -> openCarpoolPreferencesDialog());
    }

    private void initializePlacesAPI(){
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), BuildConfig.GOOGLE_MAPS_API_KEY);
        }
    }

    private void openCarpoolPreferencesDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select Carpool Preferences");

        String[] preferencesArray = {"No pets", "Quiet ride", "Music on", "No smoking", "Wheelchair accessible", "AC preferred"};
        boolean[] checkedItems = new boolean[preferencesArray.length];

        // Restore previous selections
        for (int i = 0; i < preferencesArray.length; i++) {
            checkedItems[i] = selectedPreferencesList.contains(preferencesArray[i]);
        }

        builder.setMultiChoiceItems(preferencesArray, checkedItems, (dialog, which, isChecked) -> {
            if (isChecked) {
                selectedPreferencesList.add(preferencesArray[which]);
            } else {
                selectedPreferencesList.remove(preferencesArray[which]);
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            carpoolPreferences.setText(String.join(", ", selectedPreferencesList));
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void initializeViews(){
        pickupLocation = findViewById(R.id.pickupLocation);
        destination = findViewById(R.id.dropoffLocation);
        numberOfPassengers = findViewById(R.id.passengerCount);
        departureTime = findViewById(R.id.departureTime);
        carpoolPreferences = findViewById(R.id.carpoolPreferences);
        findARideButton = findViewById(R.id.findARideButton);
        backButton = findViewById(R.id.backButtonContainer);
    }

    private void openPlaceSearch() {
        // Get user's current location (hardcoded to Kelowna, BC)
        LatLng southwest = new LatLng(49.860, -119.620);
        LatLng northeast = new LatLng(49.940, -119.530);
        RectangularBounds locationBias = RectangularBounds.newInstance(southwest, northeast);

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountries(Arrays.asList("CA"))
                .setLocationBias(locationBias)
                .build(BookRide.this);

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }


    private void openPassengerPicker() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.dialog_number_picker, null);
        bottomSheetDialog.setContentView(view);

        NumberPicker numberPicker = view.findViewById(R.id.numberPicker);
        Button confirmButton = view.findViewById(R.id.confirmButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setWrapSelectorWheel(false);

        confirmButton.setOnClickListener(v -> {
            int selectedNumber = numberPicker.getValue();
            numberOfPassengers.setText(String.valueOf(selectedNumber));
            bottomSheetDialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
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
                assert data != null;
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("BookRide", "Error: " + status.getStatusMessage());
            }
        }
    }

    private void openTimePicker() {
        // Get the current time and add 5 minutes
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5); // Minimum valid time
        int minHour = calendar.get(Calendar.HOUR_OF_DAY);
        int minMinute = calendar.get(Calendar.MINUTE);

        // Create the MaterialTimePicker
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setHour(minHour)
                .setMinute(minMinute)
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .build();

        // Set listener to update EditText when time is selected
        materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
            int selectedHour = materialTimePicker.getHour();
            int selectedMinute = materialTimePicker.getMinute();

            // Check if selected time is at least 5 minutes in the future
            Calendar selectedTime = Calendar.getInstance();
            selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
            selectedTime.set(Calendar.MINUTE, selectedMinute);

            if (selectedTime.before(calendar)) {
                Toast.makeText(this, "Departure time must be at least 5 minutes from now.", Toast.LENGTH_SHORT).show();
            } else {
                // Format the selected time and set it in the EditText
                TimeZone timeZone = TimeZone.getDefault();
                String timeZoneDisplay = timeZone.getDisplayName(false, TimeZone.SHORT);
                @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d %s", selectedHour, selectedMinute, timeZoneDisplay);
                departureTime.setText(formattedTime);
            }
        });

        // Show the time picker
        materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");
    }

}
