package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Services extends AppCompatActivity {
    private static final String TAG = "Services";
    TextView title;
    EditText newService;
    EditText serviceRole;
    Button addService;
    ListView services;
    DatabaseHelper databaseHelper;
    String service;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        title = findViewById(R.id.textView2);
        newService = findViewById(R.id.editText2);
        serviceRole = findViewById(R.id.editText4);
        addService = findViewById(R.id.addService);
        services = findViewById(R.id.services);
        databaseHelper = new DatabaseHelper(this);

        addService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                service = newService.getText().toString().trim();
                role = serviceRole.getText().toString().trim();

                Cursor data = databaseHelper.getServiceData();
                ArrayList<String> listData = new ArrayList<>();
                while (data.moveToNext()){
                    listData.add(data.getString(1));
                }

                int x = invalidService(service, role, listData);
                if (x == 1){
                    toastMessage("Please type a new service to add it");
                    return;
                } else if (x == 2){
                    toastMessage("Role must be either doctor, nurse, or staff");
                    return;
                } else if (x == 3){
                    toastMessage("This service already exists");
                    return;
                }

                databaseHelper.addService(service + ": " + role);
                Intent i = new Intent(Services.this, Services.class);
                databaseHelper.close();
                startActivity(i);
                Services.this.finish();
            }
        });

        populateListView();
    }

    public int invalidService(String service, String role, ArrayList<String> services){
        int flag = 0;
        if (service.equals("")){
            flag = 1;
        }

        if (!(role.equalsIgnoreCase("doctor") || role.equalsIgnoreCase("nurse") || role.equalsIgnoreCase("staff"))){
            flag = 2;
        }
        for (String s: services){
            if (s.equalsIgnoreCase(service + ": " + role)){
                flag = 3;
            }
        }
        return flag;
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying data in the ListView");
        Cursor data = databaseHelper.getServiceData();

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        services.setAdapter(adapter);

        services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Cursor data = databaseHelper.getServiceID(name);
                int selectedID = -1;
                while (data.moveToNext()){
                    selectedID = data.getInt(0);
                }
                Log.d(TAG, "onItemClick: You Clicked on " + name);
                Intent x = new Intent(Services.this, ChangeService.class);
                x.putExtra("id", selectedID);
                x.putExtra("service", name);
                databaseHelper.close();
                startActivity(x);
                Services.this.finish();
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
