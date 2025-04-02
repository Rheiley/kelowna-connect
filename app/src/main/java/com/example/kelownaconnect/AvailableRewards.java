package com.example.kelownaconnect;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

public class AvailableRewards extends AppCompatActivity {
    Button credit5;
    Button credit10;
    Button credit15;
    Button eGift;
    private int pointsAmount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_available_rewards);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pointsAmount = bundle.getInt("pointsAmount");

        TextView rewardPointsText = findViewById(R.id.rewardPointsText);
        rewardPointsText.setText("You have " + pointsAmount + " points"); // show amount of points user has

        LinearLayout backButton = findViewById(R.id.backButtonContainer);
        backButton.setOnClickListener(v -> {
            // Handle back button click
            finish();
        });

        credit5 = findViewById(R.id.credit5Button);
        credit10 = findViewById(R.id.credit10Button);
        credit15 = findViewById(R.id.credit15Button);
        eGift = findViewById(R.id.eGiftButton);

        credit5.setOnClickListener(v -> checkPointsHelper(500, "RC", 5));
        credit10.setOnClickListener(v -> checkPointsHelper(950, "RC", 10));
        credit15.setOnClickListener(v -> checkPointsHelper(1425, "RC", 15));
        eGift.setOnClickListener(v -> checkPointsHelper(1000, "EG", 10));

        disableButton();
    }
    public void checkPointsHelper(int requiredAmount, String type, int redeemAmount) {
        if (checkPoints(requiredAmount)) {
            redeem(type, redeemAmount, requiredAmount);
        }
        else {
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();
        }
    }
    // checks if user has enough points to redeem selected gift
    public boolean checkPoints(int requiredAmount) {
        return pointsAmount >= requiredAmount;
    }
    // type is either RC (ride credit) or EG (e-gift card) and amount is amount of RC or EG being rewarded to user
    public void redeem(String type, int rewardAmount, int requiredAmount) {
        Intent rewardDetailsActivity = new Intent(this, RewardDetails.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putInt("rewardAmount", rewardAmount);
        bundle.putInt("requiredAmount", requiredAmount);
        bundle.putInt("pointsAmount", pointsAmount);
        rewardDetailsActivity.putExtras(bundle);
        startActivity(rewardDetailsActivity);
    }
    // "disables"/greys out any button that user does not have enough points to redeem, runs once when activity start
    public void disableButton() {
        if (!checkPoints(500)) {
            credit5.setBackgroundColor(Color.GRAY);
            credit5.setAlpha(.7f);
        }
        if (!checkPoints(950)) {
            credit10.setBackgroundColor(Color.GRAY);
            credit10.setAlpha(.7f);
        }
        if (!checkPoints(1425)) {
            credit15.setBackgroundColor(Color.GRAY);
            credit15.setAlpha(.7f);
        }
        if (!checkPoints(1000)) {
            eGift.setBackgroundColor(Color.GRAY);
            eGift.setAlpha(.7f);
        }
    }
}