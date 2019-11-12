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
    String role;
    String clinic;
    EditText newService;
    EditText serviceRole;
    Button update;
    Button delete;
    DatabaseHelper databaseHelper;
    String both;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_service);

        Intent received = getIntent();
        both = received.getStringExtra("service");
        String[] split2 = both.split(": ");


        iD = received.getIntExtra("id", -1);
        list = received.getStringExtra("oldList");
        split = received.getStringArrayExtra("splitList");
        service = split2[0];
        role = split2[1];
        clinic = received.getStringExtra("clinic");

        newService = findViewById(R.id.editText3);
        serviceRole = findViewById(R.id.editText5);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        databaseHelper = new DatabaseHelper(this);

        newService.setText(service);
        serviceRole.setText(role);

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String addition = newService.getText().toString().trim() + ": " + serviceRole.getText().toString().trim();
                String addService = newService.getText().toString().trim();
                String addRole = serviceRole.getText().toString().trim();

                int x = validateServiceUpdate(addService, addRole, split);

                if (x == 1){
                    toastMessage("To update, you must enter both a service and its role.");
                    return;
                } else if (x == 2){
                    toastMessage("This service already exists");
                    return;
                } else if (x == 3) {
                    toastMessage("Role must be either doctor, nurse, or staff");
                    return;
                }

                for (int i = 0; i < split.length; i++){
                    if (split[i].equals(both)){
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
                String remove = newService.getText().toString().trim() + ": " + serviceRole.getText().toString().trim();
                int x = validateServiceDelete(remove, split);
                if (x == -1){
                    toastMessage("The service to be deleted does not exist, ensure both the service and role are entered correctly");
                    return;
                } else {
                    split[x] = null;
                }

                String newList;

                if (split[0] == null && split.length == 1){
                    newList = "";
                } else if (split[0] == null){
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

    public int validateServiceUpdate(String service, String role, String[] list){
        if (service.equals("") || role.equals("")){
            return 1;
        }

        for (int i = 0; i < list.length; i++){
            if (list[i].equalsIgnoreCase(service + ": " + role)){
                return 2;
            }
        }

        if (!(role.equalsIgnoreCase("doctor") || role.equalsIgnoreCase("nurse") || role.equalsIgnoreCase("staff"))){
            return 3;
        }
        return 0;
    }

    public int validateServiceDelete(String remove, String[] list){
        for (int i = 0; i < list.length; i++){
            if (list[i].equalsIgnoreCase(remove)){
                return i;
            }
        }
        return -1;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
