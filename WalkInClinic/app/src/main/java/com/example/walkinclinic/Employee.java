package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Employee extends AppCompatActivity {
    TextView name;
    TextView email;
    TextView phone;
    TextView user;
    TextView clinic;
    Button edit;
    String username;
    DatabaseHelper db;
    String clinicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        name = findViewById(R.id.employeeName);
        email = findViewById(R.id.email2);
        phone = findViewById(R.id.phone2);
        user = findViewById(R.id.username2);
        clinic = findViewById(R.id.clinicName);
        edit = findViewById(R.id.clinicProfile);
        db = new DatabaseHelper(this);

        Intent received = getIntent();
        username = received.getStringExtra("username");

        Cursor data = db.getEmployee(username);
        while (data.moveToNext()){
            name.setText(data.getString(1) + " " + data.getString(2));
            email.setText("Email: " + data.getString(4));
            phone.setText("Phone: " + data.getString(5));
            user.setText("Username: " + data.getString(6));
            clinic.setText("Clinic: " + data.getString(3));
            clinicName = data.getString(3);
        }
    }

    public void clinicProfile(View view){
        Intent i = new Intent(this, ClinicProfile.class);
        i.putExtra("clinicName", clinicName);
        i.putExtra("username", username);
        startActivity(i);
    }
}
