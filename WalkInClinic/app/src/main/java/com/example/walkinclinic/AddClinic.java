package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddClinic extends AppCompatActivity {

    Button addClinic;
    EditText name;
    DatabaseHelper databaseHelper;
    String clinic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);
        addClinic = findViewById(R.id.addThisClinic);
        name = findViewById(R.id.editText);
        databaseHelper = new DatabaseHelper(this);

        addClinic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clinic = name.getText().toString().trim();
                boolean insertData = databaseHelper.addNewClinic(clinic);
                if (insertData){
                    toastMessage("Clinic Successfully Added");
                } else {
                    toastMessage("Something went wrong");
                }
                Intent i = new Intent(AddClinic.this, ClinicList.class);
                startActivity(i);
            }
        });
    }

    private  void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
