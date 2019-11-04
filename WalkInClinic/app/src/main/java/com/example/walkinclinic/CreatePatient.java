package com.example.walkinclinic;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class CreatePatient extends CreatePerson {
    List<Patient> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
    }

    public void onCreatePerson(View view){
        super.onCreatePerson(view);
    }

    @Override
    void openPostLoggin() {
        //creating the string
        String postLogginString = ("Welcome " + firstName.getText().toString() + "! You are logged-in as a Patient.");
        //opening the PostLoggin class and sending the message with it
        Intent i = new Intent(this, PostLoggin.class);
        i.putExtra("message", postLogginString);
        startActivity(i);
        Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
    }

}
