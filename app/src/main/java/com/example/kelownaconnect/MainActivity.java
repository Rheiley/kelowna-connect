package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        bookRideButton.setOnClickListener(v -> {
            // Handle book ride button click
            // Start BookRide activity
            startActivity(new Intent(MainActivity.this, BookRide.class));
        });

    }
}