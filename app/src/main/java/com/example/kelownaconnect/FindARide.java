package com.example.kelownaconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FindARide extends AppCompatActivity {
    private DriverAdapter driverAdapter;
    private List<Driver> drivers;
    private List<Driver> filteredDrivers;
    private Ride ride;

    private CheckBox filterRating, filterPrice, filterETA;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_a_ride);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ride = getIntent().getParcelableExtra("ride");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        drivers = new ArrayList<>();
        filteredDrivers = new ArrayList<>(drivers);

        driverAdapter = new DriverAdapter(this, filteredDrivers, this::bookRide);

        recyclerView.setAdapter(driverAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeFilterCheckboxes();
        initializeMockData();
        setFilterListeners();

        // Back button
        LinearLayout backButtonContainer = findViewById(R.id.backButtonContainer);
        backButtonContainer.setOnClickListener(v -> finish());
    }

    private void setFilterListeners(){
        filterRating.setOnCheckedChangeListener((buttonView, isChecked) -> applyFilters());
        filterPrice.setOnCheckedChangeListener((buttonView, isChecked) -> applyFilters());
        filterETA.setOnCheckedChangeListener((buttonView, isChecked) -> applyFilters());
    }

    private void initializeMockData(){
        drivers.add(new Driver("John Doe", 4.5, 20.0, "15 mins", "Sedan", "Friendly driver", "Available", 3, 10, "English"));
        drivers.add(new Driver("Jane Smith", 4.8, 25.0, "10 mins", "SUV", "Experienced driver", "Available", 2, 20, "English, Spanish"));
        drivers.add(new Driver("Mark Lee", 4.2, 18.0, "20 mins", "Sedan", "Nice guy", "Available", 1, 5, "English, Mandarin"));
        driverAdapter.updateList(drivers);
    }

    private void initializeFilterCheckboxes(){
        filterRating = findViewById(R.id.filterRating);
        filterPrice = findViewById(R.id.filterPrice);
        filterETA = findViewById(R.id.filterETA);
    }

    private void applyFilters() {
        filteredDrivers = new ArrayList<>(drivers);

        // Apply multiple sorting criteria dynamically
        filteredDrivers.sort((d1, d2) -> {
            int result = 0;

            if (filterRating.isChecked()) {
                result = Double.compare(d2.getRating(), d1.getRating()); // Higher rating first
                if (result != 0) return result;
            }

            if (filterPrice.isChecked()) {
                result = Double.compare(d1.getEstimatedCost(), d2.getEstimatedCost()); // Lower price first
                if (result != 0) return result;
            }

            if (filterETA.isChecked()) {
                result = d1.getEta().compareTo(d2.getEta()); // Earlier ETA first
                if (result != 0) return result;
            }

            return result;
        });

        driverAdapter.updateList(filteredDrivers);
    }

    private void bookRide(Driver driver) {
        Intent intent = new Intent(this, DriverDetails.class);
        intent.putExtra("selectedDriver", driver);
        intent.putExtra("ride", ride);
        startActivity(intent);
    }
}
