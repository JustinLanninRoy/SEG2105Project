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
    boolean[] checkedItems;
    ArrayList<Integer> selectedItems = new ArrayList<>();
    DatabaseHelper db;

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
        username = received.getStringExtra("userName");
        insuranceItems = getResources().getStringArray(R.array.insurance_types);
        checkedItems = new boolean[insuranceItems.length];
        db = new DatabaseHelper(this);

        String boolItems = "";
        String intItems = "";

        Cursor data = db.getClinicData();
        while (data.moveToNext()){
            if (data.getString(1).equalsIgnoreCase(selectedClinic)){
                //toastMessage(data.getString(1));
                //toastMessage(data.getString(2));
                //toastMessage(data.getString(3));
                boolItems = data.getString(2);
                intItems = data.getString(3);
            }
        }

        String[] temp = boolItems.split(", ");
        for (int i = 0; i < insuranceItems.length; i++){
            if (temp[i].equalsIgnoreCase("true")){
                checkedItems[i] = true;
            } else {
                checkedItems[i] = false;
            }
        }
        temp = intItems.split(", ");
        for (String s: temp){
            if (!s.equals("")){
                selectedItems.add(Integer.parseInt(s));
            }
        }

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkBools = "";
                String checkints = "";
                for (Boolean b: checkedItems){
                    checkBools = checkBools + b + ", ";
                }
                for (int i: selectedItems){
                    checkints = checkints + i + ", ";
                }
                db.updateClinic(selectedClinic, checkBools, checkints);
                Intent i = new Intent(ClinicProfile.this, Employee.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
