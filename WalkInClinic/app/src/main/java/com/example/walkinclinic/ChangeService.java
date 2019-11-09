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

public class ChangeService extends AppCompatActivity {
    String list;
    String[] split;
    int iD;
    String service;
    String clinic;
    EditText newService;
    Button update;
    Button delete;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_service);

        Intent received = getIntent();
        iD = received.getIntExtra("id", -1);
        list = received.getStringExtra("oldList");
        split = received.getStringArrayExtra("splitList");
        service = received.getStringExtra("service");
        clinic = received.getStringExtra("clinic");

        newService = findViewById(R.id.editText3);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        databaseHelper = new DatabaseHelper(this);

        newService.setText(service);

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String addition = newService.getText().toString().trim();
                for (int i = 0; i < split.length; i++){
                    if (split[i].equals(service)){
                        split[i] = addition;
                    }
                }
                String newList = split[0];
                for (int i = 1; i < split.length; i++){
                    newList = newList + ", " + split[i];
                }
                databaseHelper.updateService(newList, iD, list);
                Intent i = new Intent(ChangeService.this, Services.class);
                i.putExtra("id", iD);
                i.putExtra("name", clinic);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String remove = newService.getText().toString().trim();
                boolean flag = false;
                for (int i = 0; i < split.length; i++){
                    if (split[i].equals(remove)){
                        split[i] = null;
                        flag = true;
                    }
                }
                if (!flag){
                    toastMessage("The service to be deleted does not exist");
                    return;
                }

                String newList;

                if (split[0] == null){
                     newList = split[1];
                    for (int i = 2; i < split.length; i++){
                        newList = newList + ", " + split[i];
                    }
                } else {
                    newList = split[0];
                    for (int i = 1; i < split.length; i++){
                        if (split[i] != null){
                            newList = newList + ", " + split[i];
                        }
                    }
                }

                databaseHelper.updateService(newList, iD, list);
                Intent i = new Intent(ChangeService.this, Services.class);
                i.putExtra("id", iD);
                i.putExtra("name", clinic);
                startActivity(i);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
