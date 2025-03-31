package com.example.kelownaconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static List<Ride> recentRides = new ArrayList<>();
    private RecentRidesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Views
        Button bookRideButton = findViewById(R.id.bookRideButton);
        Button offerRideButton = findViewById(R.id.offerRideButton);
        Button rewardsButton = findViewById(R.id.rewardsButton);
        Button withdrawButton = findViewById(R.id.withdrawButton);
        Button activeRidesButton = findViewById(R.id.activeRidesButton);
        Button settingsButton = findViewById(R.id.settingsButton);
        RecyclerView recyclerView = findViewById(R.id.recentRidesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create mock data for recent rides
        if (recentRides.isEmpty()) {
            recentRides.add(new Ride("UBCO", "Lake Country", "Completed", "12:30 PM"));
            recentRides.add(new Ride("Lake Country", "Vernon", "Completed", "2:00 PM"));
            recentRides.add(new Ride("Innovation Drive", "UBCO", "Completed", "10:00 AM"));
        }

        // Set the adapter
        adapter = new RecentRidesAdapter(recentRides);
        recyclerView.setAdapter(adapter);

        Ride newRide = getIntent().getParcelableExtra("newRide");
        if (newRide != null) {
            addNewRide(newRide);
        }

        bookRideButton.setOnClickListener(v -> {
            // Handle book ride button click
            // Start BookRide activity
            startActivity(new Intent(MainActivity.this, BookRide.class));
        });

    }
    @SuppressLint("NotifyDataSetChanged")
    private void addNewRide(Ride ride) {
        recentRides.add(0, ride); // Add new ride at the top
        adapter.notifyDataSetChanged();
    }
}