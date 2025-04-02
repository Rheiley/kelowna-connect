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
    private Button bookRideButton, offerRideButton, rewardsButton, withdrawButton, activeRidesButton, settingsButton;
    private RecyclerView recentRidesRecyclerView;
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

        initializeViews();
        recentRidesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializeRecentRides();
        setupOnClickListeners();

    }

    private void setupOnClickListeners(){
        bookRideButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BookRide.class));
        });
    }

    private void initializeRecentRides(){
        if (recentRides.isEmpty()) {
            recentRides.add(new Ride("UBCO", "Lake Country", "Completed", "12:30 PM"));
            recentRides.add(new Ride("Lake Country", "Vernon", "Completed", "2:00 PM"));
            recentRides.add(new Ride("Innovation Drive", "UBCO", "Completed", "10:00 AM"));
        }
        setAdapter();
        Ride newRide = getIntent().getParcelableExtra("newRide");
        if (newRide != null) {
            addNewRide(newRide);
        }
    }

    private void setAdapter(){
        adapter = new RecentRidesAdapter(recentRides);
        recentRidesRecyclerView.setAdapter(adapter);
    }

    private void initializeViews(){
        bookRideButton = findViewById(R.id.bookRideButton);
        offerRideButton = findViewById(R.id.offerRideButton);
        rewardsButton = findViewById(R.id.rewardsButton);
        withdrawButton = findViewById(R.id.withdrawButton);
        activeRidesButton = findViewById(R.id.activeRidesButton);
        settingsButton = findViewById(R.id.settingsButton);
        recentRidesRecyclerView = findViewById(R.id.recentRidesRecyclerView);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addNewRide(Ride ride) {
        recentRides.add(0, ride); // Add new ride at the top
        adapter.notifyDataSetChanged();
    }
}