package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class RateClinic extends AppCompatActivity {

    RatingBar ratingBar;
    TextView title;
    Button ok;
    String selectedClinic;
    DatabaseHelper db;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_clinic);

        Intent recieved = getIntent();
        selectedClinic = recieved.getStringExtra("clinicName");
        username = recieved.getStringExtra("username");
        db = new DatabaseHelper(this);
        ratingBar = findViewById(R.id.ratingBar);
        title = findViewById(R.id.textView44);
        ok = findViewById(R.id.button13);
        title.setText(selectedClinic);
    }

    public void okClick(View view){
        float givenRating = ratingBar.getRating();
        float currentRating = (float)0.0;
        Cursor data = db.getExistingClinic(selectedClinic);
        while (data.moveToNext()){
            currentRating = data.getFloat(12);
        }
        if (currentRating != -1){
            givenRating = (givenRating + currentRating)/2;
        }
        db.updateRating(selectedClinic, givenRating);
        Intent i = new Intent(RateClinic.this, PatientActivity.class);
        i.putExtra("username", username);
        startActivity(i);
    }
}
