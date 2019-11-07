package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void viewClinics(View view){
        Intent i = new Intent(this, ClinicList.class);
        startActivity(i);
    }

    public void viewEmployees(View view){

    }

    public void viewPatients(View view){

    }
}
