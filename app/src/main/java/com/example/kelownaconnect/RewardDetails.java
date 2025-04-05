package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RewardDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reward_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String type = bundle.getString("type");
        int rewardAmount = bundle.getInt("rewardAmount");
        int pointsAmount = bundle.getInt("pointsAmount");
        int requiredAmount = bundle.getInt("requiredAmount");

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Handle back button click
            finish();
        });

        String rewardType = "";
        String rewardDetail = "";

        TextView header = findViewById(R.id.headerText);
        TextView requiredPointsText = findViewById(R.id.requiredPointsText);
        TextView details = findViewById(R.id.detailsText);
        TextView pointsAmountText = findViewById(R.id.pointsAmountText);

        if (type.equals("RC")) {
            rewardType = "Ride Credit";
            rewardDetail = "$" + rewardAmount + " in credit to use for Kelowna Connect Rides.\n\nExpries 60 days after redemption."; // details of ride credit reward
        }
        else if (type.equals("EG")) {
            rewardType = "E-Gift Card";
            rewardDetail = "The $" + rewardAmount + " E-Gift Card will be sent to your email.\n\nCan be used anytime after redemption."; // details of e-gift card reward
        }

        // show selected reward
        header.setText("$" + rewardAmount + " " + rewardType);
        // show required amount of points to redeem selected reward
        requiredPointsText.setText(requiredAmount + " pts");
        //show details of reward being redeemed
        details.setText(rewardDetail);
        // show amount of points user has
        pointsAmountText.setText("You have " + pointsAmount + " points");

        Button redeemButton = findViewById(R.id.redeemButton);
        redeemButton.setOnClickListener(v -> {
            Intent rewardConfirmationActivity = new Intent(this, RewardConfirmation.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("type", type);
            bundle2.putInt("rewardAmount", rewardAmount);
            rewardConfirmationActivity.putExtras(bundle);
            startActivity(rewardConfirmationActivity);
        });
    }
}