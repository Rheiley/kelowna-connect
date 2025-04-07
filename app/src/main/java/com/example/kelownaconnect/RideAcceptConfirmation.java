package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RideAcceptConfirmation extends AppCompatActivity {
    TextView info, name, pass, pick, dest, eta;
    Button more, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ride_accept_confirmation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        info = findViewById(R.id.info);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        pick = findViewById(R.id.pickup);
        dest = findViewById(R.id.dest);
        eta = findViewById(R.id.eta);
        more = findViewById(R.id.more);
        home = findViewById(R.id.home);

        setupOnClickListeners();
        setDetails();
    }

    private void setupOnClickListeners() {
        more.setOnClickListener(v -> {
            startActivity(new Intent(RideAcceptConfirmation.this, Requests.class));
        });
        home.setOnClickListener(v -> {
            finish();
        });
    }
    private void setDetails() {
        name.setText(getIntent().getStringExtra("name"));
        pass.setText(getIntent().getStringExtra("pass"));
        pick.setText(getIntent().getStringExtra("pick"));
        dest.setText(getIntent().getStringExtra("dest"));
        info.setText("You have accepted " + name.getText().toString() + "'s ride request. Please head to " + pick.getText().toString() + " for pick up.");
        eta.setText("ETA: 5 minutes");
    }
}