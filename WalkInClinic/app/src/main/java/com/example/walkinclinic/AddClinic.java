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
        name = findViewById(R.id.clinicToAdd);
        databaseHelper = new DatabaseHelper(this);

        addClinic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clinic = name.getText().toString().trim();

                Cursor data = databaseHelper.getClinicData();
                ArrayList<String> listData = new ArrayList<>();
                while (data.moveToNext()){
                    listData.add(data.getString(1));
                }

                int x = invalidClinic(clinic, listData);

                if (x == 1){
                    toastMessage("Please type a new clinic to add it");
                    return;
                } else if (x == 2){
                    toastMessage("This clinic already exists");
                    return;
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

    public int invalidClinic(String clinic, ArrayList<String> list){
        if (clinic.equals("")){
            return 1;
        }
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()){
            if (iter.next().equals(clinic)){
                return 2;
            }
        }
        return 0;
    }

    private  void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
