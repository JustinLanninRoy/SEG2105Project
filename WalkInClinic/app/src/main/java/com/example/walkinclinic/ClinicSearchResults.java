package com.example.walkinclinic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ClinicSearchResults extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<String> clinicNames = new ArrayList<>();
    ArrayList<String> ratings = new ArrayList<>(); //12
    ArrayList<String> waitTimes = new ArrayList<>(); //13
    ListView listView;
    double lati;
    double longi;
    int dist;
    boolean[] services;
    int time;
    int index;
    String selected = "";
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_search_results);

        Intent recieved = getIntent();
        lati = recieved.getDoubleExtra("latitude", -1);
        longi = recieved.getDoubleExtra("longitude", -1);
        dist = recieved.getIntExtra("distance", -1);
        services = recieved.getBooleanArrayExtra("services");
        time = recieved.getIntExtra("time", -1);
        index = recieved.getIntExtra("index", -1);
        username = recieved.getStringExtra("username");

        db = new DatabaseHelper(this);
        Cursor data = db.getClinicData();
        int numsRows = data.getCount();
        if (numsRows == 0){
            toastMessage("No clinics stored.");
        } else {
            while (data.moveToNext()){
                Log.d("ClinicSearchResults", "populateListView: Displaying data in the ListView");
                double latitude = data.getDouble(10);
                double longitude = data.getDouble(11);
                double distance = -1;
                if (!(lati == 0.0 && longi == 0.0)){
                    float[] results = new float[3];
                    Location.distanceBetween(lati, longi, latitude, longitude, results);
                    distance = results[0];
                }
                String storedTimes = data.getString(8);
                String[] times = storedTimes.split(", ");
                boolean open = false;
                int compare = Integer.parseInt(times[index]);
                if (index % 2 == 0){
                    if (time > compare){
                        open = true;
                    }
                } else {
                    if (time < compare){
                        open = true;
                    }
                }
                String storedServices = data.getString(6);
                String[] stored = storedServices.split(", ");
                boolean hasServices = true;
                if (services != null && services.length > 0) {
                    for (int i = 0; i < stored.length; i++){
                        if (services[i] && !stored[i].equals("true")){
                            hasServices = false;
                        }
                    }
                }
                if (distance <= dist*1000 && open && hasServices){
                    clinicNames.add(data.getString(1));
                    ratings.add(data.getString(12));
                    waitTimes.add(data.getString(13));
                }
            }
            populateListView();
        }
    }

    private void populateListView(){
        listView = findViewById(R.id.searchList);
        final ArrayList<String> listData = new ArrayList<>();
        for (int i = 0; i < clinicNames.size(); i++){
            String rating;
            String wait;
            if (ratings.get(i).equals("-1")){
                rating = "No Ratings";
            } else {
                rating = "Rating: " + ratings.get(i) + "/5";
            }
            if (waitTimes.get(i).equals("-1")){
                wait = "0";
            } else {
                wait = waitTimes.get(i);
            }
            listData.add(clinicNames.get(i) + " – " + rating + " – Wait Time: " + wait + " minutes");
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        if (listData.size() == 0){
            toastMessage("No results found.");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                final String clinicName = name.split(" – ")[0];
                Log.d("ClinicSearchResults", "onItemClick: You Clicked on " + name);
                AlertDialog.Builder builder = new AlertDialog.Builder(ClinicSearchResults.this);
                builder.setTitle(name);
                final String[] listItems = new String[]{"Book Appointment", "Check In", "Rate Clinic"};
                builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = listItems[which];
                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!selected.equals("")){
                            if (selected.equals("Book Appointment")){
                                Intent i = new Intent(ClinicSearchResults.this, BookAppointment.class);
                                i.putExtra("username", username);
                                startActivity(i);
                            }
                            if (selected.equals("Check In")){
                                Cursor data = db.getExistingClinic(clinicName);
                                int wait = -1;
                                while (data.moveToNext()){
                                    wait = data.getInt(13);
                                }
                                if (wait == -1){
                                    wait = 15;
                                } else {
                                    wait = wait + 15;
                                }
                                db.updateWaitTime(clinicName, wait);
                                Intent i = new Intent(ClinicSearchResults.this, PatientActivity.class);
                                i.putExtra("username", username);
                                startActivity(i);
                            }
                            if (selected.equals("Rate Clinic")){
                                Intent i = new Intent(ClinicSearchResults.this, RateClinic.class);
                                i.putExtra("username", username);
                                i.putExtra("clinicName", clinicName);
                                startActivity(i);
                                //new rating window, back to search input
                            }
                            dialog.dismiss();
                        }
                    }
                });
                builder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
