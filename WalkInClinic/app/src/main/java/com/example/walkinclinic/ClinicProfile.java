package com.example.walkinclinic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClinicProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView title;
    EditText phone;
    EditText streetAddress;
    EditText postalCode;
    String boolItems = "";
    String intItems = "";
    String boolPayments = "";
    String intPayments = "";
    String boolServices = "";
    String intServices = "";
    String hoursClinic = "";
    String storedPhone = "";
    int id = -1;

    String selectedClinic;
    String username;
    String[] insuranceItems;
    String[] paymentItems;
    boolean[] checkedItems;
    ArrayList<Integer> selectedItems = new ArrayList<>();
    boolean[] checkedPayments;
    ArrayList<Integer> selectedPayments = new ArrayList<>();
    boolean[] checkedServices;
    ArrayList<Integer> selectedServices = new ArrayList<>();
    ArrayList<String> allServices;
    DatabaseHelper db;
    String finalHours;
    String[] storedHours;
    Boolean flag = false;
    List<Address> list;
    Double storedLat;
    Double storedLong;
    String[] adminServices;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    //main too big
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_profile);
        title = findViewById(R.id.textView11);
        phone = findViewById(R.id.editText);
        streetAddress = findViewById(R.id.editText6);
        postalCode = findViewById(R.id.editText7);
        Intent received = getIntent();
        selectedClinic = received.getStringExtra("clinicName");
        username = received.getStringExtra("username");
        insuranceItems = getResources().getStringArray(R.array.insurance_types);
        paymentItems = getResources().getStringArray(R.array.payment_types);
        checkedItems = new boolean[insuranceItems.length];
        checkedPayments = new boolean[paymentItems.length];
        list = new ArrayList<>();
        db = new DatabaseHelper(this);
        title.setText("Clinic: " + selectedClinic);
        new LongRunningTask().execute();
    }

    private class LongRunningTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor data = db.getExistingClinic(selectedClinic);
            while (data.moveToNext()){
                if (data.getString(1).equalsIgnoreCase(selectedClinic)){
                    id = data.getInt(0);
                    boolItems = data.getString(2);
                    intItems = data.getString(3);
                    boolPayments = data.getString(4);
                    intPayments = data.getString(5);
                    boolServices = data.getString(6);
                    intServices = data.getString(7);
                    hoursClinic = data.getString(8);
                    storedPhone = data.getString(9);
                    storedLat = data.getDouble(10);
                    storedLong = data.getDouble(11);
                }
            }
            Cursor serviceData = db.getServiceData();
            allServices = new ArrayList<>();
            while (serviceData.moveToNext()){
                allServices.add(serviceData.getString(1));
            }
            adminServices = new String[allServices.size()];
            for (int i = 0; i < allServices.size(); i++){
                adminServices[i] = allServices.get(i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!storedPhone.equals("Phone")){
                phone.setText(storedPhone);
            }
            if (storedLat != null && storedLong != null){
                reverseGeoLocate();
                if (list.size()>0){
                    Address address = list.get(0);
                    streetAddress.setText(address.getAddressLine(0));
                    postalCode.setText(address.getPostalCode());
                }
            }
            checkedServices = new boolean[allServices.size()];
            storedHours = hoursClinic.split(", ");
            checkedItems = updateBools(boolItems.split(", "), checkedItems, insuranceItems.length);
            checkedPayments = updateBools(boolPayments.split(", "), checkedPayments, paymentItems.length);
            checkedServices = updateBools(boolServices.split(", "), checkedServices, allServices.size());
            selectedItems = updateInts(intItems.split(", "), selectedItems);
            selectedPayments = updateInts(intPayments.split(", "), selectedPayments);
            selectedServices = updateInts(intServices.split(", "), selectedServices);
        }
    }

    public void insuranceClick(View view){
        AlertDialog.Builder iBuilder = new AlertDialog.Builder(ClinicProfile.this);
        iBuilder.setTitle(R.string.dialog_title);
        iBuilder.setMultiChoiceItems(insuranceItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!selectedItems.contains(position)){
                        selectedItems.add(position);
                    }
                } else if (selectedItems.contains(position)){
                    selectedItems.remove(selectedItems.indexOf(position));
                }
            }
        });
        iBuilder.setCancelable(false);
        iBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i < selectedItems.size(); i++){
                    item = item + insuranceItems[selectedItems.get(i)];
                    if(i != selectedItems.size()-1){
                        item = item + ", ";
                    }
                }
            }
        });
        iBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        iBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedItems.length; i++){
                    checkedItems[i] = false;
                    selectedItems.clear();
                }
            }
        });
        AlertDialog iDialog = iBuilder.create();
        iDialog.show();
    }

    public void paymentClick(View view){
        AlertDialog.Builder pBuilder = new AlertDialog.Builder(ClinicProfile.this);
        pBuilder.setTitle("Payment Options");
        pBuilder.setMultiChoiceItems(paymentItems, checkedPayments, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!selectedPayments.contains(position)){
                        selectedPayments.add(position);
                    }
                } else if (selectedPayments.contains(position)){
                    selectedPayments.remove(selectedPayments.indexOf(position));
                }
            }
        });
        pBuilder.setCancelable(false);
        pBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i < selectedPayments.size(); i++){
                    item = item + paymentItems[selectedPayments.get(i)];
                    if(i != selectedPayments.size()-1){
                        item = item + ", ";
                    }
                }
            }
        });
        pBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        pBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedPayments.length; i++){
                    checkedPayments[i] = false;
                    selectedPayments.clear();
                }
            }
        });
        AlertDialog pDialog = pBuilder.create();
        pDialog.show();
    }

    public void serviceClick(View view){
        AlertDialog.Builder sBuilder = new AlertDialog.Builder(ClinicProfile.this);
        sBuilder.setTitle("Available Services");
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
                    checkedPayments[i] = false;
                    selectedServices.clear();
                }
            }
        });
        AlertDialog sDialog = sBuilder.create();
        sDialog.show();
    }

    public void hoursClick(View view){
        final AlertDialog.Builder hBuilder = new AlertDialog.Builder(ClinicProfile.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_hours, null);
        final Spinner mAM = mView.findViewById(R.id.spinner2);
        final Spinner mPM = mView.findViewById(R.id.spinner3);
        final Spinner tuAM = mView.findViewById(R.id.spinner4);
        final Spinner tuPM = mView.findViewById(R.id.spinner5);
        final Spinner wAM = mView.findViewById(R.id.spinner6);
        final Spinner wPM = mView.findViewById(R.id.spinner7);
        final Spinner thAM = mView.findViewById(R.id.spinner8);
        final Spinner thPM = mView.findViewById(R.id.spinner9);
        final Spinner fAM = mView.findViewById(R.id.spinner10);
        final Spinner fPM = mView.findViewById(R.id.spinner11);
        final Spinner saAM = mView.findViewById(R.id.spinner12);
        final Spinner saPM = mView.findViewById(R.id.spinner13);
        final Spinner suAM = mView.findViewById(R.id.spinner14);
        final Spinner suPM = mView.findViewById(R.id.spinner15);
        Spinner[] spinners = {mAM, mPM, tuAM, tuPM, wAM, wPM, thAM, thPM, fAM, fPM, saAM, saPM, suAM, suPM};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ClinicProfile.this, R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < spinners.length; i++){
            spinners[i].setAdapter(adapter);
            spinners[i].setSelection(Integer.parseInt(storedHours[i]));
            spinners[i].setOnItemSelectedListener(ClinicProfile.this);
        }
        hBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing, override later
            }
        });
        hBuilder.setView(mView);
        final AlertDialog dialog = hBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monA = String.valueOf(mAM.getSelectedItemPosition());
                String monP = String.valueOf(mPM.getSelectedItemPosition());
                String tueA = String.valueOf(tuAM.getSelectedItemPosition());
                String tueP = String.valueOf(tuPM.getSelectedItemPosition());
                String wedA = String.valueOf(wAM.getSelectedItemPosition());
                String wedP = String.valueOf(wPM.getSelectedItemPosition());
                String thuA = String.valueOf(thAM.getSelectedItemPosition());
                String thuP = String.valueOf(thPM.getSelectedItemPosition());
                String friA = String.valueOf(fAM.getSelectedItemPosition());
                String friP = String.valueOf(fPM.getSelectedItemPosition());
                String satA = String.valueOf(saAM.getSelectedItemPosition());
                String satP = String.valueOf(saPM.getSelectedItemPosition());
                String sunA = String.valueOf(suAM.getSelectedItemPosition());
                String sunP = String.valueOf(suPM.getSelectedItemPosition());
                String[] times = new String[]{monA, monP, tueA, tueP, wedA, wedP, thuA, thuP, friA, friP, satA, satP, sunA, sunP};
                if (hoursValid(times)){
                    storedHours = times;
                    flag = true;
                    dialog.dismiss();
                }
            }
        });
    }

    public void saveClick(View view){
        if (isServicesOK()){
            geoLocate();
        }
        String checkBools = createBoolStrings(checkedItems);
        String checkints = createIntStrings(selectedItems);
        String paymentsBools = createBoolStrings(checkedPayments);
        String paymentints = createIntStrings(selectedPayments);
        String servicesBools = createBoolStrings(checkedServices);
        String servicesints = createIntStrings(selectedServices);
        finalHours = "";
        for (String s: storedHours){
            finalHours = finalHours + s + ", ";
        }
        if (valid()){
            Address address = list.get(0);
            db.updateClinic(selectedClinic, checkBools, checkints, paymentsBools, paymentints, servicesBools, servicesints, finalHours, phone.getText().toString().trim(), address.getLatitude(), address.getLongitude());
            Intent i = new Intent(ClinicProfile.this, Employee.class);
            i.putExtra("username", username);
            startActivity(i);
        }
    }

    public void geoLocate(){
        String addressString = streetAddress.getText().toString() + " " + postalCode.getText().toString();
        Geocoder geocoder = new Geocoder(ClinicProfile.this);
        try {
            list = geocoder.getFromLocationName(addressString, 1);
        } catch (IOException e){
        }
    }

    public void reverseGeoLocate(){
        Geocoder geocoder = new Geocoder(ClinicProfile.this);
        try {
            list = geocoder.getFromLocation(storedLat, storedLong, 1);
        } catch (IOException e){
        }
    }

    public boolean[] updateBools(String[] bools, boolean[] checked, int x){
        for (int i = 0; i < x; i++){
            if (bools[i].equalsIgnoreCase("true")){
                checked[i] = true;
            } else {
                checked[i] = false;
            }
        }
        return checked;
    }

    public ArrayList<Integer> updateInts(String[] ints, ArrayList<Integer> selected){
        for (String s: ints) {
            if (!s.equals("")) {
                selected.add(Integer.parseInt(s));
            }
        }
        return selected;
    }

    public Boolean hoursValid(String[] times){
        for (int i = 0; i < times.length; i++){
            if (times[i].equals("0")){
                if (i%2 == 0){
                    if (!times[i+1].equals("0")){
                        toastMessage("If there are days the clinic is closed, please select 'Closed' for BOTH time slots.");
                        return false;
                    }
                } else {
                    if (!times[i-1].equals("0")){
                        toastMessage("If there are days the clinic is closed, please select 'Closed' for BOTH time slots.");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String createBoolStrings(boolean[] checked){
        String ret = "";
        for (Boolean b: checked){
            ret = ret + b + ", ";
        }
        return ret;
    }

    public String createIntStrings(ArrayList<Integer> selected){
        String ret = "";
        for (int b: selected){
            ret = ret + b + ", ";
        }
        return ret;
    }

    public boolean valid(){
        if (!(list.size() > 0)){
            toastMessage("The address you entered could not be found. Please verify the address and try again.");
            return false;
        }
        if(!phone.getText().toString().trim().replace("-", "").matches("[0-9]+") || phone.getText().toString().trim().length() != 12){
            toastMessage("Please use only numbers in the XXX-XXX-XXXX format.");
            return false;
        }
        if (selectedItems.size() < 1){
            toastMessage("Please select at least one insurance company that your clinic accepts.");
            return false;
        }
        if (selectedPayments.size() < 1){
            toastMessage("Please select at least one payment method that your clinic accepts.");
            return false;
        }
        if (allServices.size() > 0 && selectedServices.size() < 1){
            toastMessage("Please select at least one service that your clinic offers.");
            return false;
        }
        if (!flag){
            toastMessage("Please verify your clinic's hours are correct.");
            return false;
        }
        return true;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean isServicesOK(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ClinicProfile.this);
        if (available == ConnectionResult.SUCCESS){
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ClinicProfile.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            toastMessage("Can't make map requests.");
        }
        return false;
    }
}
