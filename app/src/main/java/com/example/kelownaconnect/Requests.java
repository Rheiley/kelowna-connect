package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.WindowInsetsCompat;

public class Requests extends AppCompatActivity {

    CardView card1, card2, card3;
    Button acc, home;
    Offer request;
    boolean clicked = false;
    String name, pass, pick, dest;    //hard code
    Offer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        acc = findViewById(R.id.accept);
        home = findViewById(R.id.home);

        setClickableElements();
        setupOnClickListeners();
        getRequestFromIntent();

    }

    private void setClickableElements(){
        card1.setClickable(true);
        card2.setClickable(true);
        card3.setClickable(true);
        home.setClickable(true);
        acc.setClickable(true);
    }

    private void setupOnClickListeners(){
        card1.setOnClickListener(v -> {
            if(!clicked) {
                card1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grey));
                acc.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
                acc.setClickable(true);
                name = "Michael Scott";
                pass = "4";
                pick = "114 Kelowna Rd.";
                dest = "121 Costco Kelowna, BC";
                clicked = true;
            }
        });
        card2.setOnClickListener(v -> {
            if(!clicked) {
                card2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grey));
                acc.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
                acc.setClickable(true);
                name = "Bob Vance";
                pass = "2";
                pick = "4411 Fipke Rd.";
                dest = "1111 UBCO Kelowna, BC";
                clicked = true;
            }
        });
        card3.setOnClickListener(v -> {
            if(!clicked) {
                card3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grey));
                acc.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
                acc.setClickable(true);
                name = "Big Tuna";
                pass = "2";
                pick = "3001 Kelowna St.";
                dest = "101 KGH Kelowna, BC";
                clicked = true;
            }
        });

        acc.setOnClickListener(v -> {
            if(!clicked) {
                Toast.makeText(Requests.this, "Please select a request to accept", Toast.LENGTH_SHORT).show();
            } else {
                offer = new Offer(name, pass, pick, dest);
                //for now it will go to requests but it would go to MyOffers activity if we were to add it
//                confirmAccept(request);
                confirmOffer(offer);
                //send data
                Intent intent = new Intent(Requests.this, RideAcceptConfirmation.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("pass", pass);
                bundle.putString("pick", pick);
                bundle.putString("dest", dest);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getRequestFromIntent(){
        Intent intent = getIntent();
        request = intent.getParcelableExtra("offer");
    }

//    private void confirmAccept(Offer offer) {
//        new AlertDialog.Builder(this)
//                .setTitle("Confirm Booking")
//                .setMessage("Are you sure you want to accept " + name + " ?")
//                .setPositiveButton("Confirm", (dialog, which) -> {
////                    // Handle the accept confirmation
////                    Intent confirmationIntent = new Intent(Requests.this, RideAcceptConfirmation.class);
////                    confirmationIntent.putExtra("offer", request);
////                    startActivity(confirmationIntent);
//                })
//                .setNegativeButton("Cancel", (dialog, which) -> {
//                    // Handle the cancel action
//                    dialog.dismiss();
//                })
//                .show();
//    }

    private void confirmOffer(Offer offer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Requests.this);
                builder.setTitle("Confirm Accept")
                        .setMessage("Are you sure you want to accept this request?");
                builder.setPositiveButton("Confirm", (dialog, which) -> {
                    // Handle the booking confirmation
                    startActivity(new Intent(Requests.this, RideAcceptConfirmation.class));
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle the cancel action
                    dialog.dismiss();
                });
                builder.show();
    }

}