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
import android.widget.Toast;

public class RateClinic extends AppCompatActivity {

    RatingBar ratingBar;
    TextView title;
    EditText comment;
    Button ok;
    String selectedClinic;
    DatabaseHelper db;
    String username;
    int x;

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
        comment = findViewById(R.id.editText10);
        title.setText(selectedClinic);
        x = 0;
    }

    public void okClick(View view){
        checkComment(comment.getText().toString());
        if (x == 1){
            toastMessage("Press 'OK' again if you're sure you don't want to leave a comment.");
            return;
        }
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
        RateClinic.this.finish();
    }

    public void checkComment(String comment){
        if (comment.trim().isEmpty() && x == 0){
            x = 1;
        } else {
            x = 0;
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
