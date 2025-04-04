package com.example.kelownaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText reviewText;
    Button submitReview;
    CheckBox[] tagBoxes;

    LinearLayout backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ratingBar = findViewById(R.id.ratingBar);
        reviewText = findViewById(R.id.reviewText);
        submitReview = findViewById(R.id.submitReview);
        backButton = findViewById(R.id.backButton);

        tagBoxes = new CheckBox[]{
                findViewById(R.id.tagLayout).findViewWithTag("Punctual"),
                findViewById(R.id.tagLayout).findViewWithTag("Friendly"),
                findViewById(R.id.tagLayout).findViewWithTag("Clean Car"),
                findViewById(R.id.tagLayout).findViewWithTag("Late Arrival"),
                findViewById(R.id.tagLayout).findViewWithTag("Safe Driver"),
                findViewById(R.id.tagLayout).findViewWithTag("Comfortable Seats"),
                findViewById(R.id.tagLayout).findViewWithTag("Rude")
        };

        submitReview.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String review = reviewText.getText().toString().trim();
            int selectedTags = 0;

            for (CheckBox box : tagBoxes) {
                if (box.isChecked()) selectedTags++;
            }

            if (rating == 0) {
                Toast.makeText(this, "Please provide a rating", Toast.LENGTH_SHORT).show();
                return;
            }

            if (review.isEmpty()) {
                Toast.makeText(this, "Please write a review", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedTags == 0) {
                Toast.makeText(this, "Please select at least one tag", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedTags > 3) {
                Toast.makeText(this, "Select up to 3 tags only!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Optional: Save review/rating somewhere

            startActivity(new Intent(ReviewActivity.this, ReviewConfirmationActivity.class));
        });



        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewActivity.this, RideDetailsActivity.class);
            startActivity(intent);
        });
    }
}
