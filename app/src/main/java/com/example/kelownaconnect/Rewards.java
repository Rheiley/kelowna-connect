package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Rewards extends AppCompatActivity {
    private int pointsAmount = 1200; // hardcoded amount of points
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rewards);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView rewardPointsText = findViewById(R.id.rewardPointsText);
        rewardPointsText.setText("You have " + pointsAmount + " points"); // show amount of points user has

        LinearLayout backButton = findViewById(R.id.backButtonContainer);
        backButton.setOnClickListener(v -> {
            // Handle back button click
            finish();
        });

        Button availableRewBtn = findViewById(R.id.availableRewButton);
        Button redeemedRewBtn = findViewById(R.id.redeemedRewButton);
        Button rewardHistBtn = findViewById(R.id.rewardHistButton);
        Button withdrawBtn = findViewById(R.id.withdrawButton);

        availableRewBtn.setOnClickListener(v -> {
            // start new activity that shows available Rewards
            Intent availableRewardsActivity = new Intent(this, AvailableRewards.class);
            availableRewardsActivity.putExtra("pointsAmount", pointsAmount);
            startActivity(availableRewardsActivity);
        });

        // show toast message for the unimplemented buttons/tasks
        redeemedRewBtn.setOnClickListener(v -> comingSoon());
        rewardHistBtn.setOnClickListener(v -> comingSoon());
        withdrawBtn.setOnClickListener(v -> comingSoon());

    }

    //toast message for the unimplemented buttons/tasks
    public void comingSoon() {
        Toast.makeText(this, "Feature Coming Soon!", Toast.LENGTH_SHORT).show();
    }
}