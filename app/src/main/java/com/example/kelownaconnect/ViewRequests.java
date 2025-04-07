package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewRequests extends AppCompatActivity {
    Button browse, acc, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        browse = findViewById(R.id.breq);
        acc = findViewById(R.id.areq);
        home = findViewById(R.id.home);

        browse.setOnClickListener(v -> {
            startActivity(new Intent(ViewRequests.this, Requests.class));
        });
        acc.setOnClickListener(v -> {
            Toast.makeText(ViewRequests.this, "Feature coming soon :D", Toast.LENGTH_SHORT).show();
        });
        home.setOnClickListener(v -> finish());
    }
}