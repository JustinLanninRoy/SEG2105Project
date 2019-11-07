package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class ClinicList extends AppCompatActivity {

    TextView appletree;
    TextView rideau;
    TextView bank;
    TextView uOttawa;
    TextView southbank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list);
        appletree = findViewById(R.id.appletree);
        rideau = findViewById(R.id.rideau);
        bank = findViewById(R.id.bank);
        uOttawa = findViewById(R.id.uOttawa);
        southbank = findViewById(R.id.southbank);

        appletree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
