package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminsClinicProfile extends AppCompatActivity {
    private String selectedClinic;
    private int selectedID;
    TextView clinicTitle;
    TextView address;
    TextView phone;
    Button delete;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_clinic);
        clinicTitle = findViewById(R.id.textView18);
        address = findViewById(R.id.textView19);
        phone = findViewById(R.id.textView20);
        delete = findViewById(R.id.button4);
        databaseHelper = new DatabaseHelper(this);

        Intent received = getIntent();
        selectedID = received.getIntExtra("id", -1);
        selectedClinic = received.getStringExtra("name");
        clinicTitle.setText(selectedClinic);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteClinic(selectedID);
                Intent i = new Intent(AdminsClinicProfile.this, ClinicList.class);
                startActivity(i);
            }
        });
    }
}
