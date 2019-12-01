package com.example.walkinclinic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView name;
    EditText streetAddress;
    EditText postalCode;
    Spinner weekDay;
    Spinner hour;
    Spinner ampm;
    Spinner distance;
    String[] adminServices;
    ArrayList<String> allServices;
    boolean[] checkedServices;
    ArrayList<Integer> selectedServices = new ArrayList<>();
    DatabaseHelper db;
    List<Address> list = new ArrayList<>();
    Geocoder geocoder;
    String username;
    ImageView logout;
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        name = findViewById(R.id.textView28);
        streetAddress = findViewById(R.id.editText6);
        postalCode = findViewById(R.id.editText7);
        weekDay = findViewById(R.id.spinner16);
        hour = findViewById(R.id.spinner17);
        ampm = findViewById(R.id.spinner18);
        distance = findViewById(R.id.spinner19);
        logout = findViewById(R.id.imageView6);
        search = findViewById(R.id.imageView2);
        db = new DatabaseHelper(this);
        geocoder = new Geocoder(PatientActivity.this);

        Intent recieved = getIntent();
        username = recieved.getStringExtra("username");
        Cursor patient = db.getPatient(username);
        while (patient.moveToNext()){
            name.setText(patient.getString(1) + " " + patient.getString(2));
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PatientActivity.this, R.array.times2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hour.setAdapter(adapter);
        hour.setOnItemSelectedListener(PatientActivity.this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(PatientActivity.this, R.array.days, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekDay.setAdapter(adapter1);
        weekDay.setOnItemSelectedListener(PatientActivity.this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(PatientActivity.this, R.array.time, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ampm.setAdapter(adapter2);
        ampm.setOnItemSelectedListener(PatientActivity.this);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(PatientActivity.this, R.array.distances, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distance.setAdapter(adapter3);
        distance.setOnItemSelectedListener(PatientActivity.this);

        Cursor serviceData = db.getServiceData();
        allServices = new ArrayList<>();
        while (serviceData.moveToNext()){
            allServices.add(serviceData.getString(1));
        }
        adminServices = generateServices();
    }

    public String[] generateServices(){
        adminServices = new String[allServices.size()];
        if (allServices.size() == 0){
            adminServices = null;
            checkedServices = null;
        } else {
            for (int i = 0; i < allServices.size(); i++){
                adminServices[i] = allServices.get(i);
            }
            checkedServices = new boolean[adminServices.length];
        }
        return adminServices;
    }

    public void selectServices(View view){
        AlertDialog.Builder sBuilder = new AlertDialog.Builder(PatientActivity.this);
        sBuilder.setTitle("Select Services");
        sBuilder.setMultiChoiceItems(adminServices, checkedServices, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!selectedServices.contains(position)){
                        selectedServices.add(position);
                    }
                } else if (selectedServices.contains(position)){
                    selectedServices.remove(selectedServices.indexOf(position));
                }
            }
        });
        sBuilder.setCancelable(false);
        sBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i < selectedServices.size(); i++){
                    item = item + adminServices[selectedServices.get(i)];
                    if(i != selectedServices.size()-1){
                        item = item + ", ";
                    }
                }
            }
        });
        sBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        sBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedServices.length; i++){
                    checkedServices[i] = false;
                    selectedServices.clear();
                }
            }
        });
        AlertDialog sDialog = sBuilder.create();
        sDialog.show();
    }

    public void search(View view){
        search.setBackgroundResource(R.drawable.common_google_signin_btn_icon_dark_normal_background);
        String addressString = streetAddress.getText().toString() + " " + postalCode.getText().toString();
        geoLocate(addressString);
        Address address = null;
        if (list.size() > 0){
            address = list.get(0);
        }
        int dist = Integer.parseInt(distance.getSelectedItem().toString());
        int time = hour.getSelectedItemPosition();
        int index;
        int d = weekDay.getSelectedItemPosition();
        String x = ampm.getSelectedItem().toString();
        if (x.equals("AM")){
            index = 2*d;
        } else {
            index = (2*d) +1;
        }
        Intent i = new Intent(PatientActivity.this, ClinicSearchResults.class);
        if (list.size() > 0){
            i.putExtra("latitude",address.getLatitude());
            i.putExtra("longitude", address.getLongitude());
        } else {
            i.putExtra("latitude", 0.0);
            i.putExtra("longitude", 0.0);
        }
        i.putExtra("distance", dist);
        i.putExtra("services", checkedServices);
        i.putExtra("time", time + 1);
        i.putExtra("index", index);
        i.putExtra("username", username);
        db.close();
        startActivity(i);
    }

    //try testing
    public void geoLocate(String addressString){
        try {
            list = geocoder.getFromLocationName(addressString, 1);
        } catch (IOException e){
            toastMessage("Location not found, continuing search with other inputs.");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    public void logOut(View view){
        logout.setBackgroundResource(R.drawable.common_google_signin_btn_icon_dark_normal_background);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        PatientActivity.this.finish();
    }
}
