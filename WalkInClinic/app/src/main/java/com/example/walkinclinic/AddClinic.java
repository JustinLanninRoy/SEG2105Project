package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

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

                Cursor data = databaseHelper.getClinicData();
                ArrayList<String> listData = new ArrayList<>();
                while (data.moveToNext()){
                    listData.add(data.getString(1));
                }

                if (clinic.equals("")){
                    toastMessage("Please type a new clinic to add it");
                    return;
                }

                Iterator<String> iter = listData.iterator();

                while (iter.hasNext()){
                    if (iter.next().equals(clinic)){
                        toastMessage("This clinic already exists");
                        return;
                    }
                }

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
