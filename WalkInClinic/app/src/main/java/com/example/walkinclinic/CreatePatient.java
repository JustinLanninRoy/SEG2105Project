package com.example.walkinclinic;

import android.os.Bundle;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CreatePatient extends CreatePerson {
    List<Patient> patients;
    DatabaseReference databasePatients;
    EditText a;
    EditText zip;
    EditText streetAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
        databasePatients = FirebaseDatabase.getInstance().getReference("patients");
        patients = new ArrayList<>();
    }

    public void onCreatePerson(View view){
        super.onCreatePerson(view);
        a = findViewById(R.id.Age);
        zip = findViewById(R.id.postalCode);
        streetAddress = findViewById(R.id.StreetAddress);
        addPatient();
    }

    @Override
    protected void onStart() {

        super.onStart();
        databasePatients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Patient patient = postSnapshot.getValue(Patient.class);
                    patients.add(patient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    private void addPatient() {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String address = streetAddress.getText().toString().trim();
        String postalCode = zip.getText().toString().trim();
        String value = a.getText().toString().trim();
        int age = Integer.parseInt(value);
        String email = Email.getText().toString().trim();
        String phone = Phone.getText().toString().trim();
        String user = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String id = databasePatients.push().getKey();
        Patient patient = new Patient(id, fName, lName, address, postalCode, age, email, phone, user, password);
        databasePatients.child(id).setValue(patient);
    }

}
