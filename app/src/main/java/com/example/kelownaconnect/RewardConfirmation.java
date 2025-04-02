package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RewardConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reward_confirmation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String type = bundle.getString("type");
        int rewardAmount = bundle.getInt("rewardAmount");
        String rewardType = "";
        if (type.equals("RC")) {
            rewardType = "Ride Credit";
        }
        else if (type.equals("EG")) {
            rewardType = "E-Gift Card";
        }
        TextView confirmationText = findViewById(R.id.confirmationText);
        confirmationText.setText("Your reward of $" + rewardAmount + " " + rewardType + " has been successfully redeemed!");  // show confirmation text

        Button homeBtn = findViewById(R.id.homeButton);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        Button redeemedRewBtn = findViewById(R.id.redeemedRewButton);
        redeemedRewBtn.setOnClickListener(v -> Toast.makeText(this, "Feature Coming Soon!", Toast.LENGTH_SHORT).show());
    }
}