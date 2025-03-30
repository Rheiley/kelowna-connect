package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewConfirmationActivity extends AppCompatActivity {

    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_confirmation);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            // Replace with your actual home activity
            startActivity(new Intent(ReviewConfirmationActivity.this, MainActivity.class));
        });
    }
}
