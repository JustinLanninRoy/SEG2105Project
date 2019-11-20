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
    int iD;
    String service;
    String role;
    EditText newService;
    EditText serviceRole;
    Button update;
    Button delete;
    DatabaseHelper databaseHelper;
    String both;
    ArrayList<String> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_service);

        Intent received = getIntent();
        both = received.getStringExtra("service");
        String[] split2 = both.split(": ");

        iD = received.getIntExtra("id", -1);
        service = split2[0];
        role = split2[1];

        newService = findViewById(R.id.editText3);
        serviceRole = findViewById(R.id.editText5);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        databaseHelper = new DatabaseHelper(this);

        newService.setText(service);
        serviceRole.setText(role);

        Cursor data = databaseHelper.getServiceData();

        listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));
        }

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String addition = newService.getText().toString().trim() + ": " + serviceRole.getText().toString().trim();
                String addService = newService.getText().toString().trim();
                String addRole = serviceRole.getText().toString().trim();

                int x = validateServiceUpdate(addService, addRole, listData);

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

                databaseHelper.updateService(addition, iD, service + ": " + role);
                Intent i = new Intent(ChangeService.this, Services.class);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String remove = newService.getText().toString().trim() + ": " + serviceRole.getText().toString().trim();
                int x = validateServiceDelete(remove, listData);
                if (x == -1){
                    toastMessage("The service to be deleted does not exist, ensure both the service and role are entered correctly");
                    return;
                }

                databaseHelper.deleteService(iD);
                Intent i = new Intent(ChangeService.this, Services.class);
                startActivity(i);
            }
        });
    }

    public int validateServiceUpdate(String service, String role, ArrayList<String> list){
        if (service.equals("") || role.equals("")){
            return 1;
        }

        for (String s: list){
            if (s.equalsIgnoreCase(service + ": " + role)){
                return 2;
            }
        }

        if (!(role.equalsIgnoreCase("doctor") || role.equalsIgnoreCase("nurse") || role.equalsIgnoreCase("staff"))){
            return 3;
        }
        return 0;
    }

    public int validateServiceDelete(String remove, ArrayList<String> list){
        for (String s: list){
            if (s.equalsIgnoreCase(remove)){
                return 1;
            }
        }
        return -1;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
