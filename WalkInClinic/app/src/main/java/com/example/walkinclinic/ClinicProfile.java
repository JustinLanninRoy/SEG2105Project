package com.example.walkinclinic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClinicProfile extends AppCompatActivity {
    TextView title;
    EditText phone;
    EditText address;
    Button insurance;
    Button payment;
    Button services;
    Button hours;
    Button save;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_profile);

        title = findViewById(R.id.textView11);
        phone = findViewById(R.id.editText);
        address = findViewById(R.id.editText6);
        insurance = findViewById(R.id.button5);
        payment = findViewById(R.id.button6);
        services = findViewById(R.id.button7);
        hours = findViewById(R.id.button8);
        save = findViewById(R.id.button9);

        Intent received = getIntent();
        selectedClinic = received.getStringExtra("clinicName");
        username = received.getStringExtra("username");
        insuranceItems = getResources().getStringArray(R.array.insurance_types);
        paymentItems = getResources().getStringArray(R.array.payment_types);
        checkedItems = new boolean[insuranceItems.length];
        checkedPayments = new boolean[paymentItems.length];
        db = new DatabaseHelper(this);

        String boolItems = "";
        String intItems = "";

        String boolPayments = "";
        String intPayments = "";

        String boolServices = "";
        String intServices = "";

        String hoursClinic = "";
        int id = -1;

        String storedPhone = "";
        String storedAddress = "";

        Cursor data = db.getClinicData();
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
                storedAddress = data.getString(10);
            }
        }
        title.setText(selectedClinic + " – ClinicID: " + id);
        phone.setText(storedPhone);
        address.setText(storedAddress);

        Cursor serviceData = db.getServiceData();
        allServices = new ArrayList<>();
        while (serviceData.moveToNext()){
            allServices.add(serviceData.getString(1));
        }
        final String[] adminServices = new String[allServices.size()];
        for (int i = 0; i < allServices.size(); i++){
            adminServices[i] = allServices.get(i);
        }
        checkedServices = new boolean[allServices.size()];
        storedHours = hoursClinic.split(", ");

        checkedItems = updateBools(boolItems.split(", "), checkedItems, insuranceItems.length);
        checkedPayments = updateBools(boolPayments.split(", "), checkedPayments, paymentItems.length);
        checkedServices = updateBools(boolServices.split(", "), checkedServices, allServices.size());
        selectedItems = updateInts(intItems.split(", "), selectedItems);
        selectedPayments = updateInts(intPayments.split(", "), selectedPayments);
        selectedServices = updateInts(intServices.split(", "), selectedServices);

        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder hBuilder = new AlertDialog.Builder(ClinicProfile.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_hours, null);
                final EditText mAM = mView.findViewById(R.id.editText7);
                final EditText mPM = mView.findViewById(R.id.editText9);
                final EditText tuAM = mView.findViewById(R.id.editText77);
                final EditText tuPM = mView.findViewById(R.id.editText99);
                final EditText wAM = mView.findViewById(R.id.editText23);
                final EditText wPM = mView.findViewById(R.id.editText25);
                final EditText thAM = mView.findViewById(R.id.editText28);
                final EditText thPM = mView.findViewById(R.id.editText30);
                final EditText fAM = mView.findViewById(R.id.editText34);
                final EditText fPM = mView.findViewById(R.id.editText36);
                final EditText saAM = mView.findViewById(R.id.editText39);
                final EditText saPM = mView.findViewById(R.id.editText41);
                final EditText suAM = mView.findViewById(R.id.editText45);
                final EditText suPM = mView.findViewById(R.id.editText47);

                if (storedHours.length > 1){
                    mAM.setText(storedHours[0]);
                    mPM.setText(storedHours[1]);
                    tuAM.setText(storedHours[2]);
                    tuPM.setText(storedHours[3]);
                    wAM.setText(storedHours[4]);
                    wPM.setText(storedHours[5]);
                    thAM.setText(storedHours[6]);
                    thPM.setText(storedHours[7]);
                    fAM.setText(storedHours[8]);
                    fPM.setText(storedHours[9]);
                    saAM.setText(storedHours[10]);
                    saPM.setText(storedHours[11]);
                    suAM.setText(storedHours[12]);
                    suPM.setText(storedHours[13]);
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
                        String monA = mAM.getText().toString().trim();
                        String monP = mPM.getText().toString().trim();
                        String tueA = tuAM.getText().toString().trim();
                        String tueP = tuPM.getText().toString().trim();
                        String wedA = wAM.getText().toString().trim();
                        String wedP = wPM.getText().toString().trim();
                        String thuA = thAM.getText().toString().trim();
                        String thuP = thPM.getText().toString().trim();
                        String friA = fAM.getText().toString().trim();
                        String friP = fPM.getText().toString().trim();
                        String satA = saAM.getText().toString().trim();
                        String satP = saPM.getText().toString().trim();
                        String sunA = suAM.getText().toString().trim();
                        String sunP = suPM.getText().toString().trim();
                        String[] times = new String[]{monA, monP, tueA, tueP, wedA, wedP, thuA, thuP, friA, friP, satA, satP, sunA, sunP};
                        if (hoursValid(times)){
                            storedHours = times;
                            flag = true;
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    db.updateClinic(selectedClinic, checkBools, checkints, paymentsBools, paymentints, servicesBools, servicesints, finalHours, phone.getText().toString().trim(), address.getText().toString().trim());
                    Intent i = new Intent(ClinicProfile.this, Employee.class);
                    i.putExtra("username", username);
                    startActivity(i);
                }
            }
        });
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
            if (times[i].equals("")){
                toastMessage("Please ensure each field is filled in.");
                return false;
            }
            if (times[i].equals("*")){
                if (i%2 == 0){
                    if (!times[i+1].equals("*")){
                        toastMessage("If there are days the clinic is closed, please enter '*' in BOTH time slots.");
                        return false;
                    }
                } else {
                    if (!times[i-1].equals("*")){
                        toastMessage("If there are days the clinic is closed, please enter '*' in BOTH time slots.");
                        return false;
                    }
                }
            }
            if (times[i].length() == 5){
                if (!Character.isDigit(times[i].charAt(0)) || !Character.isDigit(times[i].charAt(1)) || times[i].charAt(2) != ':' || !Character.isDigit(times[i].charAt(3)) || !Character.isDigit(times[i].charAt(4))){
                    toastMessage("Please ensure times are written in the 00:00 format, or enter '*' for days that the clinic is closed.");
                    return false;
                }
            } else if (times[i].length() == 1) {
                if (!times[i].equals("*")){
                    toastMessage("Please ensure times are written in the 00:00 format, or enter '*' for days that the clinic is closed.");
                    return false;
                }
            } else {
                toastMessage("Please ensure times are written in the 00:00 format, or enter '*' for days that the clinic is closed.");
                return false;
            }
            if (!times[i].equals("*")) {
                if (Integer.parseInt(times[i].split(":")[0])>12 || Integer.parseInt(times[i].split(":")[1])>60) {
                    toastMessage("Please ensure the times you entered are valid on the 12 hour clock.");
                    return false;
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
        if(!phone.getText().toString().trim().replace("-", "").matches("[0-9]+") || phone.getText().toString().trim().length() != 12){
            toastMessage("Please use only numbers in the XXX-XXX-XXXX format.");
            return false;
        }
        if (address.getText().toString().trim().isEmpty() || address.getText().toString().trim().equalsIgnoreCase("address")){
            toastMessage("Please fill in the address field.");
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
}
