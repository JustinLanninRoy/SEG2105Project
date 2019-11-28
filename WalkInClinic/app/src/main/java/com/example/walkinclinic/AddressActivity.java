package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {
    TextView title;
    EditText streetAddress;
    EditText postalCode;
    Button save;
    String selectedClinic;
    String username;
    DatabaseHelper db;
    Double storedLat;
    Double storedLong;
    List<Address> list = new ArrayList<>();
    Geocoder geocoder;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Intent received = getIntent();
        title = findViewById(R.id.textView25);
        streetAddress = findViewById(R.id.editText8);
        postalCode = findViewById(R.id.editText9);
        save = findViewById(R.id.button10);
        selectedClinic = received.getStringExtra("clinicName");
        username = received.getStringExtra("username");
        db = new DatabaseHelper(this);
        title.setText("Clinic: " + selectedClinic);
        geocoder = new Geocoder(AddressActivity.this);
        toastMessage(String.valueOf(geocoder.isPresent()));

        new LongRunningTask().execute();
    }

    private class LongRunningTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor data = db.getExistingClinic(selectedClinic);
            while (data.moveToNext()){
                if (data.getString(1).equalsIgnoreCase(selectedClinic)){
                    storedLat = data.getDouble(10);
                    storedLong = data.getDouble(11);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if ((!storedLat.equals(0.0) || !storedLong.equals(0.0))){
                reverseGeoLocate();
                if (list.size()>0){
                    Address address = list.get(0);
                    streetAddress.setText(address.getAddressLine(0));
                    postalCode.setText(address.getPostalCode());
                }
            }
        }
    }

    public void geoLocate(){
        String addressString = streetAddress.getText().toString() + " " + postalCode.getText().toString();
        try {
            list = geocoder.getFromLocationName(addressString, 1);
        } catch (IOException e){
            toastMessage("The address you entered could not be found. Please verify the address and try again.");
        }
    }

    public void reverseGeoLocate(){
        try {
            list = geocoder.getFromLocation(storedLat, storedLong, 1);
        } catch (IOException e){
            toastMessage("Could not find address");
        }
    }

    public void save(View view){
        geoLocate();
        if (list.size() > 0) {
            Address address = list.get(0);
            db.updateLatitue(selectedClinic, address.getLatitude());
            db.updateLogitude(selectedClinic, address.getLongitude());
            Intent i = new Intent(AddressActivity.this, Employee.class);
            i.putExtra("username", username);
            db.close();
            startActivity(i);
            AddressActivity.this.finish();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
