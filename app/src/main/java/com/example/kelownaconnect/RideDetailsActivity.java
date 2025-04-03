package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class RideDetailsActivity extends AppCompatActivity {

    private Button reviewButton;
    private LinearLayout backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_details);

        reviewButton = findViewById(R.id.reviewButton);
        backButton = findViewById(R.id.backButton);

        reviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(RideDetailsActivity.this, ReviewActivity.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(RideDetailsActivity.this, MainActivity.class);
            startActivity(intent);
            });
    }
}
