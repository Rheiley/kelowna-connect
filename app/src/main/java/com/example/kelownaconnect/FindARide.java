package com.example.kelownaconnect;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FindARide extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DriverAdapter driverAdapter;
    private List<Driver> drivers;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_a_ride);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        Button ratingFilterButton = findViewById(R.id.ratingFilterButton);
        Button priceFilterButton = findViewById(R.id.priceFilterButton);
        Button etaFilterButton = findViewById(R.id.etaFilterButton);

        drivers = new ArrayList<>();
        driverAdapter = new DriverAdapter(this, drivers, driver -> bookRide(driver));

        recyclerView.setAdapter(driverAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add mock driver data
        drivers.add(new Driver("John Doe", 4.5, 20.0, "15 mins", "Sedan", 20));
        drivers.add(new Driver("Jane Smith", 4.8, 25.0, "10 mins", "SUV", 15));
        drivers.add(new Driver("Mark Lee", 4.2, 18.0, "20 mins", "Sedan", 2));

        driverAdapter.notifyDataSetChanged();

        // Set up filter buttons
        ratingFilterButton.setOnClickListener(v -> applyFilters("Rating"));
        priceFilterButton.setOnClickListener(v -> applyFilters("Price"));
        etaFilterButton.setOnClickListener(v -> applyFilters("ETA"));
    }

    private void bookRide(Driver driver) {
        Toast.makeText(this, "Booked with " + driver.getName(), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void applyFilters(String filter) {
        switch (filter) {
            case "Rating":
                drivers.sort((d1, d2) -> Double.compare(d2.getRating(), d1.getRating()));
                break;
            case "Price":
                drivers.sort(Comparator.comparingDouble(Driver::getEstimatedCost));
                break;
            case "ETA":
                drivers.sort(Comparator.comparing(Driver::getEta));
                break;
        }
        driverAdapter.notifyDataSetChanged();
    }
}
