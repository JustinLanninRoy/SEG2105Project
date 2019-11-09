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
    int selectedID;
    String selectedClinic;
    String service;
    String role;
    String[] split;
    String list;

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
        Intent received = getIntent();
        selectedID = received.getIntExtra("id", -1);
        selectedClinic = received.getStringExtra("name");
        title.setText(selectedClinic + " Services");

        addService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                service = newService.getText().toString().trim();
                role = serviceRole.getText().toString().trim();

                if (service.equals("")){
                    toastMessage("Please type a new service to add it");
                    return;
                }

                if (!(role.equalsIgnoreCase("doctor") || role.equalsIgnoreCase("nurse") || role.equalsIgnoreCase("staff"))){
                    toastMessage("Role must be either doctor, nurse, or staff");
                    return;
                }

                Cursor data = databaseHelper.getServiceData();
                ArrayList<String> listData = new ArrayList<>();
                while (data.moveToNext()){
                    listData.add(data.getString(1));
                }
                String list = listData.get(selectedID-1);

                String[] split = list.split(", ");

                for (String s: split){
                    if (s.equals(service)){
                        toastMessage("This service already exists");
                        return;
                    }
                }

                if (list.equals("")){
                    databaseHelper.updateService(list + service + ": " + role, selectedID, list);
                } else {
                    databaseHelper.updateService(list + ", " + service  + ": " + role, selectedID, list);
                }
                Intent i = new Intent(Services.this, Services.class);
                i.putExtra("id", selectedID);
                i.putExtra("name", selectedClinic);
                startActivity(i);
            }
        });

        populateListView();
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying data in the ListView");
        Cursor data = databaseHelper.getServiceData();

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));
        }

        list = listData.get(selectedID-1);
        split = list.split(", ");

        if (list.equals("")){
            return;
        }

        ArrayList<String> serviceData = new ArrayList<>();

        for (String s: split){
            serviceData.add(s);
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, serviceData);
        services.setAdapter(adapter);

        services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);
                Intent x = new Intent(Services.this, ChangeService.class);
                x.putExtra("oldList", list);
                x.putExtra("splitList", split);
                x.putExtra("id", selectedID);
                x.putExtra("service", name);
                x.putExtra("clinic", selectedClinic);
                startActivity(x);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
