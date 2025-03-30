package com.example.kelownaconnect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindARide extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DriverAdapter driverAdapter;
    private List<Driver> drivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_a_ride);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        drivers = new ArrayList<>();
        driverAdapter = new DriverAdapter(this, drivers, driver -> {
            // Handle book button click
            bookRide(driver);
        });

        recyclerView.setAdapter(driverAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mock data for testing
        drivers.add(new Driver("John Doe", 4.5, 20.0, "15 mins", "Sedan"));
        drivers.add(new Driver("Jane Smith", 4.8, 25.0, "10 mins", "SUV"));
        drivers.add(new Driver("Mark Lee", 4.2, 18.0, "20 mins", "Sedan"));

        driverAdapter.notifyDataSetChanged();
    }

    private void bookRide(Driver driver) {
        // Implement the booking functionality (e.g., navigating to the booking confirmation screen)
        Toast.makeText(this, "Booked with " + driver.getName(), Toast.LENGTH_SHORT).show();
    }
}
