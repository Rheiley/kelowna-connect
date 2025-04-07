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
import androidx.appcompat.app.AlertDialog;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class OfferARide extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private EditText destination, numberOfSeats, departureTime, cost, activeLocationField;
    private Offer offer;
    Button offerRideButton;
    LinearLayout backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_offer_aride);
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
        destination.setClickable(true);
        numberOfSeats.setClickable(true);
        departureTime.setClickable(true);
        cost.setClickable(true);
    }

    private void setFocusableElements(){
        destination.setFocusable(false);
        numberOfSeats.setFocusable(false);
        departureTime.setFocusable(false);
        cost.setFocusable(false);
    }

    private void setupOnClickListeners(){
        backButton.setOnClickListener(v -> finish());
        offerRideButton.setOnClickListener(v -> {
            // Get input values
            String destination = this.destination.getText().toString().trim();
            String numberOfSeats = this.numberOfSeats.getText().toString().trim();
            String departureTime = this.departureTime.getText().toString().trim();
            String cost = this.cost.getText().toString().trim();

            // Check for empty fields and show a toast for missing inputs
            if (destination.isEmpty()) {
                Toast.makeText(OfferARide.this, "Please enter a destination", Toast.LENGTH_SHORT).show();
                return;
            }
            if (numberOfSeats.isEmpty()) {
                Toast.makeText(OfferARide.this, "Please enter the number of passengers", Toast.LENGTH_SHORT).show();
                return;
            }
            if (departureTime.isEmpty()) {
                Toast.makeText(OfferARide.this, "Please select a departure time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (cost.isEmpty()) {
                Toast.makeText(OfferARide.this, "Please enter your rate", Toast.LENGTH_SHORT).show();
                return;
            }

            offer = new Offer(destination, Integer.parseInt(numberOfSeats), departureTime, Integer.parseInt(cost));
            //for now it will go to requests but it would go to MyOffers activity if we were to add it
            Intent intent = new Intent(this, Requests.class);
            intent.putExtra("offer", offer);
            startActivity(intent);
        });
        destination.setOnClickListener(v -> {
            activeLocationField = destination;
            openPlaceSearch();
        });
        numberOfSeats.setOnClickListener(v -> openSeatsPicker());
        departureTime.setOnClickListener(v -> openTimePicker());
        offerRideButton.setOnClickListener(v -> confirmOffer(offer));
    }

    private void initializePlacesAPI(){
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), BuildConfig.GOOGLE_MAPS_API_KEY);
        }
    }

    private void initializeViews(){
        destination = findViewById(R.id.destLocation);
        numberOfSeats = findViewById(R.id.seatCount);
        departureTime = findViewById(R.id.depTime);
        cost = findViewById(R.id.costRate);
        offerRideButton = findViewById(R.id.offerRideButton);
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
                .build(OfferARide.this);

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }
    private void openSeatsPicker() {
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
            numberOfSeats.setText(String.valueOf(selectedNumber));
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
                Log.i("OfferRide", "Selected Place: " + place.getName() + ", " + place.getLatLng());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                assert data != null;
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("OfferRide", "Error: " + status.getStatusMessage());
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
    private void confirmOffer(Offer offer) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Booking")
                .setMessage("Are you sure you want to offer this ride?")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // Handle the booking confirmation
                    Intent confirmationIntent = new Intent(OfferARide.this, RideOfferConfirmation.class);
                    confirmationIntent.putExtra("offer", offer);
                    startActivity(confirmationIntent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle the cancel action
                    dialog.dismiss();
                })
                .show();
    }
}